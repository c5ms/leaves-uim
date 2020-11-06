package com.leaves.queue.topic;

import com.leansoft.bigqueue.FanOutQueueImpl;
import com.leaves.queue.core.AbstractLifecycle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Setter
@Getter
@RequiredArgsConstructor
public class Topic extends AbstractLifecycle {

    private String dataDir;
    private String queueName;

    private FanOutQueueImpl queue;

    @Override
    protected void doStart() throws Exception {
        this.queue = new FanOutQueueImpl(dataDir, queueName);
    }

    @Override
    protected void doStop() throws Exception {
        this.queue.close();
    }

    /**
     * 向主题发布消息
     *
     * @param data 消息
     * @throws IOException -
     */
    public void publish(byte[] data) throws IOException {
        this.queue.enqueue(data);
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