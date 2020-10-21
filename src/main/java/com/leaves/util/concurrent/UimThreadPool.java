package com.leaves.util.concurrent;


import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UimThreadPool {

    /**
     * 工作任务
     */
    private final LinkedList<Runnable> jobs;
    /**
     * 工作线程
     */
    private final List<Worker> workers;

    /**
     * 异常处理器
     */
    private final UimExceptionHandler exceptionHandler;

    /**
     * 线程创建工厂
     */
    private final ThreadFactory threadFactory;

    /**
     * 构造函数,默认异常处理器
     *
     * @param size 线程池容量
     * @see DefaultUimExceptionHandler
     */
    public UimThreadPool(int size, UimExceptionHandler exceptionHandler, ThreadFactory threadFactory) {
        this.exceptionHandler = null == exceptionHandler ? new DefaultUimExceptionHandler() : exceptionHandler;

        this.threadFactory = null == threadFactory ? new DefaultThreadFactory() : threadFactory;

        this.jobs = new LinkedList<>();

        this.workers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            newWorker();
        }

    }

    /**
     * 新建一个工人
     */
    private void newWorker() {

        Worker worker = new Worker();
        this.workers.add(worker);

        Thread thread = this.threadFactory.newThread(worker);
        thread.start();
    }


    public void submit(Runnable job) {
        synchronized (jobs) {
            jobs.add(job);
            jobs.notify();
        }
    }

    /**
     * 终止线程池运行,并且等待任务结束
     *
     * @param wait 是否等待任务结束
     */
    public void shutdown(boolean wait) {
        for (Worker worker : this.workers) {
            worker.shutdown();
        }

        if (wait) {
            // TODO 等待线程池任务结束
        }
    }

    public int getWaitSize() {
        return this.jobs.size();
    }

    private class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            Runnable job;
            while (running) {
                // 等待一个任务提交进来
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }


                // 获取到任务.开始执行任务
                if (null != job) {
                    try {
                        job.run();
                    } catch (Throwable th) {
                        exceptionHandler.handle(th);
                    }
                }

            }
        }

        /**
         * 终止工作
         */
        public void shutdown() {
            this.running = false;
        }
    }


    /**
     * 默认异常处理器
     */
    private static class DefaultUimExceptionHandler implements UimExceptionHandler {

        @Override
        public void handle(Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认线程工厂
     */
    private static class DefaultThreadFactory implements ThreadFactory {

        private final static String prefix = "uim-thread-";
        private final AtomicLong idx = new AtomicLong();

        @Override
        public Thread newThread(@NotNull Runnable r) {

            Thread thread = new Thread(r, prefix + idx.incrementAndGet());
            thread.setDaemon(false);

            return thread;
        }
    }

}
