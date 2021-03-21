package com.eqshen.keepsimple.java.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 滑动窗口算法实现
 * 设计目标：x长度的时间窗口内，最多处理y个请求
 */
@Slf4j
public class SlideWindow {
    /**
     * 时间窗口，秒
     */
    private int timeWindow;

    private int taskLimit;

    private volatile  boolean shutdownFlag;


    private ConcurrentLinkedQueue<Long> queue;

    public SlideWindow(int timeWindow,int taskLimit){
        this.timeWindow = timeWindow;
        this.taskLimit = taskLimit;
        this.queue = new ConcurrentLinkedQueue<>();
        this.shutdownFlag = false;

        //内置线程定时清除窗口中的过期数据
        Thread thread = new Thread(() -> {
            while (!shutdownFlag){
                long validThreshold = System.currentTimeMillis() - timeWindow * taskLimit * 1000;
                Long ttl;

                while ((ttl = queue.peek()) != null && ttl < validThreshold) {
                    log.info("clear:{}-{}={}", validThreshold,ttl,validThreshold - ttl);
                    queue.poll();
                }
                sleepSometime(timeWindow * 1000);
            }
        });
        thread.start();
    }

    public boolean takeToken(){
        synchronized (queue){
            int validSize = validSize();
            if(validSize > taskLimit){
                log.info("====> time window is full");
                return false;
            }
            log.info("[slide window] current valid size:{}",validSize);
            queue.offer(System.currentTimeMillis());
        }
        return true;
    }

    public void shutdown(){
        this.shutdownFlag = true;
    }

    public int validSize(){
        long threshold = System.currentTimeMillis() - timeWindow * 1000;
        int realSize = 0;
        final Iterator<Long> iterator = queue.iterator();
        while (iterator.hasNext()){
            long next = iterator.next();
            if(next <= threshold){
                realSize++;
            }else{
                break;
            }
        }
        return realSize;
    }

    private static void sleepSometime(long millSec){
        try {
            Thread.sleep(millSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SlideWindow sw = new SlideWindow(1,10);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        IntStream.range(0,10).forEach(i->{
            AtomicInteger taskCnt = new AtomicInteger(15);
            Thread thread = new Thread(() -> {
                while (taskCnt.get() > 0){
                    sleepSometime(new Random().nextInt(6)*1000+500);
                    log.info("thread take toke:{},left task:{}",sw.takeToken(),taskCnt.decrementAndGet());
                }
                countDownLatch.countDown();
            });
            thread.start();
        });

        countDownLatch.await();
        log.info("three task finished");
        for (int i = 0; i < 5; i++) {
            log.info("sw valid size:{}",sw.validSize());
            sleepSometime(1000);
        }
        sleepSometime(50000);
        sw.shutdown();
        log.info("============ sw valid size:{}",sw.validSize());
        log.info("slid window stopped !!!");
    }
}
