package com.eqshen.keepsimple.java.lock;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ThreadInteract {
    volatile static List<Integer> list = new ArrayList<>();
    static Thread producer = null;


    /**
     * 实现一个功能：
     * 线程1 负责往list中加入第
     * @param args
     */
    public static void main(String[] args) {
        success2();
//        System.out.println("===============");
//        fail();
    }

    public static void fail(){
        Thread producerLocal = null;

        //effective final
        Thread finalProducerLocal = producerLocal;
        Thread watcher = new Thread(()->{
            if(list.size() != 5){
                LockSupport.park();
                sleep(2);
                System.out.println("watcher end");
                LockSupport.unpark(finalProducerLocal);
            }

        });


        producerLocal = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("list add: "+ i);
                if(list.size() == 5){
                    LockSupport.unpark(watcher);
                    LockSupport.park();
                }

                //        sleep(1);
            }
        });

        watcher.start();
        //sleep(1);
        producerLocal.start();
    }

    public static void success(){
        Thread watcher = new Thread(()->{
            if(list.size() != 5){
                LockSupport.park();
                sleep(2);
                System.out.println("watcher end");
                LockSupport.unpark(producer);
            }
        });


        producer = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("list add: "+ i);
                if(list.size() == 5){
                    LockSupport.unpark(watcher);
                    LockSupport.park();
                }

                //        sleep(1);
            }
        });

        //二者启动顺序没要求
        watcher.start();
        producer.start();
    }

    public static void success2(){
        Object lock = new Object();

        Thread watcher = new Thread(()->{
            synchronized (lock){
                try {
                    if(list.size() != 5){
                        lock.wait();
                        System.out.println("watcher end");
                    }
                    lock.notifyAll();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread localProducer = new Thread(()->{
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    list.add(i);
                    System.out.println("list add: "+ i);
                    if(list.size() == 5){
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        //这种实现，必须让观察者先启动
        watcher.start();
        sleep(1);
        localProducer.start();

    }


    @SneakyThrows
    public static void sleep(int sec){
        TimeUnit.SECONDS.sleep(sec);
    }
}
