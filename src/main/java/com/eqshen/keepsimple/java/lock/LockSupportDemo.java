package com.eqshen.keepsimple.java.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    /**
     * LockSupport的park与unpark的学习使用
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        test1();
        test2();
    }

    public static void test1() throws InterruptedException {
        System.out.println("============test1 start=============");
        Thread thread = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5){
                    System.out.println(i + " park");
                    LockSupport.park();
                    System.out.println(i + " after park");
                }
                sleep(1);
            }
        });
        thread.start();
        System.out.println("Main thread begin to sleep");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("Main thread wake up");
        LockSupport.unpark(thread);

        thread.join();
        System.out.println("============test1 end===============");
    }

    public static void test2() throws InterruptedException {
        System.out.println("============test2 start=============");
        Thread thread = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5){
                    System.out.println(i + " park");
                    LockSupport.park();
                    System.out.println(i + " after park");
                }

                if(i == 8){
                    System.out.println(i + " park");
                    LockSupport.park();
                    System.out.println(i + " after park");
                }

                sleep(1);
            }
        });
        thread.start();
        LockSupport.unpark(thread);

        sleep(16);
        System.out.println("2 years later,let's wake up thread");
        LockSupport.unpark(thread);

        thread.join();
        System.out.println("============test2 end===============");
    }

    public static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
