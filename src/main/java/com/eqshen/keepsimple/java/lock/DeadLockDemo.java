package com.eqshen.keepsimple.java.lock;

import com.eqshen.keepsimple.java.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DeadLockDemo extends BaseTest {

    private  Object resource1 = new Object();

    private  Object resource2 = new Object();

    private ReentrantLock reentrantLock1 = new ReentrantLock();

    private ReentrantLock reentrantLock2 = new ReentrantLock();

    /**
     * 一个简单死锁的例子
     * @throws InterruptedException
     */
    public void deadLock() throws InterruptedException {
        Thread th1 = new Thread(() -> {
            try{
                synchronized (resource1){
                    log.info("线程 {} 获取锁 resource1 成功",Thread.currentThread().getId());
                    Thread.sleep(1000);

                    synchronized (resource2){
                        log.info("线程 {} 获取锁 resource2 成功",Thread.currentThread().getId());
                    }
                }
            }catch (InterruptedException e){
                log.error("线程 {} 被中断了",Thread.currentThread().getId());
            }


        });

        Thread th2 = new Thread(() -> {
            try{
                synchronized (resource2){
                    log.info("线程 {} 获取锁 resource2 成功",Thread.currentThread().getId());

                    Thread.sleep(1000);

                    synchronized (resource1){
                        log.info("线程 {} 获取锁 resource1 成功",Thread.currentThread().getId());
                    }
                }
            }catch (InterruptedException e){
                log.error("线程 {} 被中断了",Thread.currentThread().getId());
            }


        });
        //start
        th1.start();
        th2.start();
        log.info("线程启动，主线程休眠2000ms");
        Thread.sleep(2000);
        log.info("do interrupt");
        //这里的中断是无效的
        th1.interrupt();
        th2.interrupt();
        th1.join();
        th2.join();
    }

    @Test
    public void testDeadLock() throws InterruptedException {
        deadLock();
    }


    /**
     * 采用 可中断的lock
     */
    public void interruptedLock() throws InterruptedException {

        Thread th1 = new Thread(()->{
            try {

                reentrantLock1.lockInterruptibly();
                log.info("{} 获取锁 reentrantLock1",Thread.currentThread().getId());
                Thread.sleep(1000);

                reentrantLock2.lockInterruptibly();
                log.info("{} 获取锁 reentrantLock2",Thread.currentThread().getId());

            } catch (InterruptedException e) {
                log.error("{} 锁被中断了",Thread.currentThread().getId(),e);
            }finally {
                //记得unlock
                if(reentrantLock1.isHeldByCurrentThread()){
                    reentrantLock1.unlock();
                }
                if(reentrantLock2.isHeldByCurrentThread()){
                    reentrantLock2.unlock();
                }
            }
        });


        Thread th2 = new Thread(()->{
            try {

                reentrantLock2.lockInterruptibly();
                log.info("{} 获取锁 reentrantLock2",Thread.currentThread().getId());

                Thread.sleep(1000);
                reentrantLock1.lockInterruptibly();
                log.info("{} 获取锁 reentrantLock1",Thread.currentThread().getId());

            } catch (InterruptedException e) {
                log.error("{} 锁被中断了",Thread.currentThread().getId(),e);
            }finally {
                //记得unlock
                if(reentrantLock1.isHeldByCurrentThread()){
                    reentrantLock1.unlock();
                }
                if(reentrantLock2.isHeldByCurrentThread()){
                    reentrantLock2.unlock();
                }
            }
        });

        th1.start();
        th2.start();
        Thread.sleep(2000);

        //发生死锁，发出中断信号
        th1.interrupt();

        th1.join();
        th2.join();
    }

    @Test
    public void testInterruptedLock() throws InterruptedException {
        interruptedLock();
    }



}
