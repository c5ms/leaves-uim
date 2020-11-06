package com.leaves.queue.core;

/**
 * 标记一个有生命周期的类
 */
public interface Lifecycle {
    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 检测运行状态
     *
     * @return true 表示运行种
     */
    boolean isRunning();
}
