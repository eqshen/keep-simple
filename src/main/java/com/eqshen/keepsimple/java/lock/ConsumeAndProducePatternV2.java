package com.eqshen.keepsimple.java.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模式
 */
@Slf4j
public class ConsumeAndProducePatternV2<T> {
    private LinkedList<T> list = new LinkedList<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition produceCondition = lock.newCondition();

    private Condition consumerCondition = lock.newCondition();

    /**
     * 容器最大容量
     */
    private int containerLimit;

    private int currentCount;

    public ConsumeAndProducePatternV2(int containerLimit){
        this.containerLimit = containerLimit;
        this.currentCount = 0;
    }

    @SneakyThrows
    public T get(){
        T val = null;
        try{
            lock.lock();
            while (currentCount == 0){
                log.info("container is empty, wait...");
                this.consumerCondition.await();
            }
            currentCount--;
            val = list.getLast();
            return val;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            log.info("[C] {} - {} - consume successfully: {}",currentCount,Thread.currentThread().getName(),val);
//            sleep(800);
            this.produceCondition.signalAll();
            lock.unlock();
        }
        return null;
    }

    public void put(T val){
        try{
            lock.lock();
            while (currentCount == containerLimit){
                log.info("container is full, stop producing...");
                this.produceCondition.await();
            }
            this.list.addLast(val);
            currentCount++;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            log.info("[P] {} - {} - produce successfully",currentCount,Thread.currentThread().getName());
//            sleep(800);
            this.consumerCondition.signalAll();
            lock.unlock();
        }
    }

    public static void sleep(int sec){
        try {
//            log.info("sleep: {}",sec);
            TimeUnit.MILLISECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        ConsumeAndProducePatternV2<String> cpp = new ConsumeAndProducePatternV2<>(6);

        Runnable consumer = ()->{
            while (true){
                String val = cpp.get();

                int interval = (int) (Math.random()*10000 + 1000);
                sleep(interval);
            }
        };

        Runnable producer = ()->{
            while (true){
                String val = UUID.randomUUID().toString();
                cpp.put(val);

                int interval = (int) (Math.random()*10000 + 1000);
                sleep(interval);
            }
        };

        //create  consumer threads
        for (int i = 0; i < 6; i++) {
            new Thread(consumer,"c"+i).start();
        }

        //create  producer threads
        Thread thread = null;
        for (int i = 0; i < 6; i++) {
            thread = new Thread(producer, "p" + i);
            thread.start();
        }

        //let main thread wait here.
        thread.join();
    }
}
