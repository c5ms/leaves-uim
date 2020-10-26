package com.leaves.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class MutexTest {

    static Lock mutex = new Mutex();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Task());
        thread1.setName("thread1");

        Thread thread2 = new Thread(new Task());
        thread2.setName("thread2");

        thread1.start();
        thread2.start();

        TimeUnit.SECONDS.sleep(1);

        thread2.interrupt();

    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                mutex.lock();
                mutex.lock();
                mutex.lock();
                for (int i = 1; i <= 5; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "::" + i);
                        TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                mutex.unlock();
            }
        }
    }

}
