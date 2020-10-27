package com.leaves.experiment;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueUsage {
    public static void main(String[] args) {
        DelayQueue<Message> delayQueue = new DelayQueue<>();
        delayQueue.put(new Message(1000, "1"));
        delayQueue.put(new Message(2000, "2"));
        delayQueue.put(new Message(3000, "3"));

        try {
            while (!delayQueue.isEmpty()) {
                System.out.println(delayQueue.take().data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }

    private static class Message implements Delayed {
        /**
         * 延迟时间
         */
        private final long delayTime;
        /**
         * 到期时间
         */
        private final long expire;
        /**
         *  数据
         */
        private String data;


        public Message(long delayTime,String data){
            this.delayTime=delayTime;
            this.data=data;
            // 过期时间为：当前系统时间+延迟时间
            this.expire=System.currentTimeMillis()+delayTime;
        }



        @Override
        public long getDelay(@NotNull TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(@NotNull Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) -o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayedElement{" + "delay=" + delayTime +
                    ", expire=" + expire +
                    ", data='" + data + '\'' +
                    '}';
        }
    }
}