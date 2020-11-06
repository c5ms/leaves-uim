package com.leaves.queue.topic;

import com.leansoft.bigqueue.FanOutQueueImpl;
import com.leaves.queue.core.AbstractLifecycle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

@Slf4j
@Setter
@Getter

public class Topic extends AbstractLifecycle {

    private final String dataDir;
    private final String queueName;
    private final FanOutQueueImpl queue;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition hasNew = lock.newCondition();

    @Builder
    public Topic(String dataDir, String queueName) throws IOException {
        this.dataDir = dataDir;
        this.queueName = queueName;

        this.queue = new FanOutQueueImpl(dataDir, queueName);
    }

    @Override
    protected void doStart() throws Exception {

    }

    @Override
    protected void doStop() throws Exception {
        this.queue.close();
    }

    /**
     * 向主题发布消息
     *
     * @param data 消息
     * @return 如果正确投递消息, 则返回消息的编号,否则返回-1
     * @throws IOException -
     */
    public long publish(byte[] data) throws IOException, InterruptedException {
        if (this.isRunning()) {
            long index = this.queue.enqueue(data);
            lock.lockInterruptibly();
            try {
                hasNew.signalAll();
            } finally {
                lock.unlock();
            }
            return index;
        }
        return -1;
    }

    /**
     * 获取一个消息
     *
     * @param subscriber 订阅者身份编号
     * @param timeout    超时时长
     * @param unit       时间单位
     * @return 如果在超时时间内获取到了消息, 则返回消息, 否则返回 null
     * @throws IOException -
     */
    public byte[] take(String subscriber, long timeout, TimeUnit unit) throws IOException, InterruptedException {
        if (timeout <= 0) {
            return queue.dequeue(subscriber);
        }
        long nanos = unit.toNanos(timeout);
        byte[] bytes = queue.dequeue(subscriber);
        if (null != bytes) {
            return bytes;
        }
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            hasNew.awaitNanos(nanos);
            return queue.dequeue(subscriber);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 查看主题中有多少消息
     *
     * @return 消息数量
     */
    public long size() {
        return this.queue.size();
    }

}