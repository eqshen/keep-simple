package com.eqshen.keepsimple.java.lock;

import com.eqshen.keepsimple.java.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class AQSRelevantDemo extends BaseTest {

    @Test
    public void testCountdown() throws InterruptedException {
        /*
        * 场景：老师监考，收卷时，要等所有学生交完卷后，才能散场。
        * 提前交卷的学生需要等待
        * */
        //一共30个学生
        int studentCount = 30;
        CountDownLatch countDownLatch = new CountDownLatch(studentCount);

        //监考老师1，负责发卷和划水
        Thread teacherTh1 = new Thread(()->{
            System.out.println("教师1 发卷 完成，等待学生全部答题结束");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("教师1 收卷");

        });
        teacherTh1.start();

        //监考老师2，负责在考场里抓作弊的人
        Thread teacherTh2 = new Thread(()->{
            System.out.println("教师2 开始巡逻，检查准考证");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("教师2 监考结束");

        });
        teacherTh2.start();


        for (int i = 0; i < studentCount; i++) {
            new Thread(()->{
                String thName = "线程 "+ Thread.currentThread().getName();
                int needTime = (int)(Math.random()*3000+100);
                System.out.println(thName + " 答卷ing...需要时长："+needTime);
                try {

                    Thread.sleep(needTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thName+" 完成答卷");
                countDownLatch.countDown();

            },"stu"+i).start();
        }

        teacherTh1.join();
        teacherTh2.join();
        System.out.println("所有学生都交卷了，大家回家吃饭吧~");
    }

    @Test
    public void testCyclicBarrier() throws InterruptedException {
        /*
        * 场景：公司团建，进行登山活动
        * 在山顶设置集结点， 并将员工分成若干组。
        * 要求每组中的所有成员都到达山顶都，该组任务算完成，才可以进行下一个任务
        * */
        int groupMemberCnt = 10;
        AtomicInteger totalUseTime = new AtomicInteger();

        Thread judgeThread = new Thread(() -> System.out.println("所有组员都已到达集结点，你们总耗时："+ totalUseTime.get()));

        CyclicBarrier barrier = new CyclicBarrier(groupMemberCnt, judgeThread);

        for (int i = 0; i < groupMemberCnt; i++) {
            new Thread(()->{
                String thName = Thread.currentThread().getName();
                int needTime = (int)(Math.random()*3000+100);
                System.out.println(thName + " 开始登山，预计耗时："+ needTime);
                try {
                    Thread.sleep(needTime);
                    totalUseTime.addAndGet(needTime);
                    System.out.println(thName + " 已经到达集结点，等待其他组员");
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            },"组员-"+i).start();
        }
        Thread.sleep(3000);
        judgeThread.join();

    }


    @SneakyThrows
    @Test
    public void testCyclicBar(){
        Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    Thread.sleep(1000);
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acc");
                    semaphore.release();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    Thread.sleep(1000);
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acc");
                    semaphore.release();
                }
            }
        },"t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
