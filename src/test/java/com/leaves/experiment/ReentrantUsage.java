package com.leaves.experiment;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantUsage {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        System.out.println("main lock");

        reentrantLock.lock();
        System.out.println("main lock");

        reentrantLock.unlock();
        System.out.println("main unlock");

        reentrantLock.unlock();
        System.out.println("main unlock");


        new Thread(() -> {
            reentrantLock.lock();
            System.out.println("thread run");
            reentrantLock.unlock();
        }).start();

    }
}
