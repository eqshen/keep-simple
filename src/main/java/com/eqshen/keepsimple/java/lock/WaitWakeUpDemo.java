package com.eqshen.keepsimple.java.lock;

import com.eqshen.keepsimple.java.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class WaitWakeUpDemo extends BaseTest {

    private Object lock = new Object();

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


}
