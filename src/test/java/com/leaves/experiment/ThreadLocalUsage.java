package com.leaves.experiment;


import java.util.concurrent.TimeUnit;

public class ThreadLocalUsage {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<ThreadLocalValue> threadLocal = new ThreadLocal<>();
        new Thread(() -> {
            threadLocal.set(new ThreadLocalValue("test-value-1"));
        }).start();

        while (true) {
            TimeUnit.SECONDS.sleep(1);
        }

    }

    static class ThreadLocalValue {
        private String value;

        public ThreadLocalValue(String value) {
            this.value = value;
        }
    }

}
