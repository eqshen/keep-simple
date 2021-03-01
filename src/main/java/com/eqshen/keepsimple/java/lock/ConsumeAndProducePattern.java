package com.eqshen.keepsimple.java.lock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author eqshen
 * @description
 * @date 2021/2/25
 */
@Slf4j
public class ConsumeAndProducePattern<T> {
    private LinkedList<T> queue = new LinkedList<>();

    private int maxThreshold;

    private Object lock = new Object();

    public ConsumeAndProducePattern(int limit){
        this.maxThreshold = limit;
    }

    /**
     * 消费
     * @return
     */
    @SneakyThrows
    public T consume(){
        synchronized (lock){
            while (queue.isEmpty()){
                log.info("queue is empty");
                lock.wait();
            }
            T res = queue.removeFirst();
            lock.notifyAll();
            return res;
        }
    }

    @SneakyThrows
    public void produce(T ele){
        synchronized (lock){
            //full
            while (queue.size()  == maxThreshold){
                log.info("queue is full:{}",maxThreshold);
                lock.wait();
            }
            queue.addLast(ele);
            lock.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //定义3个消费者，2个生产者，模拟随机生产和消费
        ConsumeAndProducePattern<String> capp = new ConsumeAndProducePattern<>(10);

        Runnable consumer = () -> {
            while (true){
                String res = capp.consume();
                log.info("[Consume] res:{}",res);
                randomSleep();
            }
        };

        Runnable producer = () -> {
            while (true){
                String res = System.currentTimeMillis()+"";
                log.info("[Produce] source:{}",res);
                capp.produce(res);
                randomSleep();
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(consumer,"c"+i).start();
        }

        Thread p = null;
        for (int i = 0; i < 2; i++) {
            p = new Thread(producer,"p"+i);
            p.start();
        }

        p.join();
    }

    @SneakyThrows
    public static void randomSleep(){
        int t = (int)(Math.random() * 1500) + 500;
        Thread.sleep(t);
    }
}
