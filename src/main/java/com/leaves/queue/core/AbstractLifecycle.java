package com.leaves.queue.core;


public abstract class AbstractLifecycle implements Lifecycle {
    protected final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    private volatile boolean running = false;
    private volatile boolean stopped = false;

    /**
     * 执行程序启动操作
     *
     * @throws Exception 当启动失败的时候
     */
    protected abstract void doStart() throws Exception;

    /**
     * 执行程序停止操作,注意,这个方法会吞噬InterruptedException,然后保持挂起状态.
     *
     * @throws Exception 当启动失败的时候
     */
    protected abstract void doStop() throws Exception;

    @Override
    public synchronized void start() {
        if (!this.running) {
            try {
                this.stopped = false;
                this.running = true;
                this.doStart();
            } catch (Throwable e) {
                if (!this.stopped) {
                    this.running = false;
                    throw new RuntimeException("start bean exception", e);
                }
            }
        }
    }

    /**
     * start方法有可能是持续性的
     * 结束后要等待start方法结束
     */
    @Override
    public void stop() {
        if (this.running) {
            try {
                this.stopped = true;
                this.doStop();
                this.running = false;
            } catch (Exception e) {
                throw new RuntimeException("stop bean exception", e);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return this.running && !this.stopped;
    }



}
