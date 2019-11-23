package com.eqshen.keepsimple.java.lock;

import com.eqshen.keepsimple.java.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class WaitWakeUpDemo extends BaseTest {

    private Object lock = new Object();

    private ReentrantLock reentrantLock = new ReentrantLock();

    private Condition condition1 = reentrantLock.newCondition();

    private Condition condition2 = reentrantLock.newCondition();

    private Condition condition3 = reentrantLock.newCondition();

    @Test
    public void testNotify() throws InterruptedException {
        Thread th1 = new Thread(()->{
            log.info("线程 {} 启动",Thread.currentThread().getName());
            synchronized (lock){
                log.info("线程 {} 获取到锁",Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                    log.info("线程 {} 活干到一半休息,释放锁",Thread.currentThread().getName());
                    lock.wait();
                    log.info("线程 {} 被唤醒继续执行",Thread.currentThread().getName());
                    Thread.sleep(1000);
                    log.info("线程 {} 执行结束",Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.error("Thread Interrupted Exception",e);
                }
            }

        },"啊花");

        Thread th2 = new Thread(() -> {
            log.info("线程 {} 启动",Thread.currentThread().getName());
            synchronized (lock){
                log.info("线程 {} 获取到锁",Thread.currentThread().getName());
                log.info("线程 {} 处理其他任务,结束，notify其他线程",Thread.currentThread().getName());
                lock.notify();
                log.info("线程 {} notify后继续处理剩余任务",Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("线程 {} 执行结束，释放锁",Thread.currentThread().getName());
            }

        },"小明");

        th1.start();
        Thread.sleep(200);//保证让 啊花 先获取到锁
        th2.start();
        th2.join();
        th1.join();
    }

    @Test
    public void testNewNotify() throws InterruptedException {
        Runnable worker = () -> {
            log.info("线程 {} 启动",Thread.currentThread().getName());
            reentrantLock.lock();
            log.info("线程 {} 资源空闲 - 获取到锁",Thread.currentThread().getName());
            log.info("线程 {} 开始干活",Thread.currentThread().getName());
            log.info("线程 {} 好累😫，开始偷懒",Thread.currentThread().getName());
            try {
                if(Thread.currentThread().getName().equals("打工仔1")){
                    condition1.await();
                }else if(Thread.currentThread().getName().equals("打工仔2")){
                    condition2.await();
                }
            } catch (InterruptedException e) {
                log.info("中断异常",e);
            }
            log.info("线程 {} 继续干活，并且加班奋斗",Thread.currentThread().getName());
            condition3.signalAll();
            reentrantLock.unlock();
            log.info("线程 {} 释放锁，下班了~",Thread.currentThread().getName());
        };

        Runnable boss = () -> {


            try {
                log.info("线程 {} 启动, 老板 来了",Thread.currentThread().getName());
                reentrantLock.lock();
                log.info("线程 {} 发现资源空闲了，进来看看 - 获取到锁",Thread.currentThread().getName());
                log.info("线程 {} 发现工人都在偷懒，怒吼一声：起来修福报！",Thread.currentThread().getName());
                log.info("线程 {} 大喊：打工仔1 你先给我把xxxx处理了",Thread.currentThread().getName());
                condition1.signalAll();
                Thread.sleep(2000);

                log.info("线程 {} 大喊：打工仔2 你再给我把ooooo处理了",Thread.currentThread().getName());
                condition2.signalAll();
                log.info("线程 {} 开始监工 -_-",Thread.currentThread().getName());
                condition3.await();
                log.info("线程 {} 满意的下班了",Thread.currentThread().getName());
                reentrantLock.unlock();
            } catch (InterruptedException e) {
                log.info("中断异常",e);
            }


        };


        Thread th1 = new Thread(worker,"打工仔1");
        Thread th2 = new Thread(worker,"打工仔2");
        Thread bossTh = new Thread(boss,"包工头");
        th1.start();
        th2.start();
        Thread.sleep(1000);
        bossTh.start();

        th1.join();
        th2.join();
        bossTh.join();

    }


}
