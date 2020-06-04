package com.eqshen.keepsimple.java.basic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者模式实现
 */
public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        InnerQueue queue = new InnerQueue(new LinkedList<>(),3);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        AtomicInteger msgSeq = new AtomicInteger(1);

        //开两个线程，一个生产，一个消费
        Thread pThread = new Thread(() -> {
             while (true){
                 producer.send(new Msg(String.valueOf(msgSeq.get()),null));
                 msgSeq.getAndIncrement();
                 int rand = (int) (Math.random()*10*1000);
                 try {
                     System.out.println("[Producer] 休息："+rand);
                     Thread.sleep(rand);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
        });

        Thread cThread = new Thread(()->{
            while (true){
                consumer.consume();
                int rand = (int) (Math.random()*10*1000);
                try {
                    System.out.println("[Consumer] 休息："+rand);
                    Thread.sleep(rand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //begin
        pThread.start();
        cThread.start();

        //pThread.join();
    }
}

class Msg{
    String msgId;
    Object data;
    public Msg(String msgId,Object data){
        this.msgId = msgId;
        this.data = data;
    }
}

class InnerQueue{
    Queue<Msg> queue;
    int capacity;

    public InnerQueue(Queue<Msg> queue,int capacity){
        this.queue = queue;
        this.capacity = capacity;
    }

    public boolean isFull(){
        return queue.size() == capacity;
    }

    public boolean isEmpty(){
        return queue.size() == 0;
    }
}

class Producer{
    private InnerQueue iq;

    public Producer(InnerQueue iq){
        this.iq = iq;
    }

    public void send(Msg msg){
        synchronized (iq){
            if(iq.isFull()){
                try {
                    System.out.println("[Producer]队列已满，等待消费者消费");
                    iq.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            iq.queue.add(msg);
            System.out.println("[Producer]生产成功:"+msg.msgId);
            iq.notify();
        }
    }
}

class Consumer{
    private InnerQueue iq;
    public Consumer(InnerQueue iq){
        this.iq = iq;
    }

    public void consume(){
        synchronized (iq){
            if(iq.isEmpty()){
                try {
                    System.out.println("[Consumer] 队列为空，等待生产者生产");
                    iq.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Msg msg = iq.queue.poll();
            System.out.println("[Consumer] 消费成功："+ msg.msgId);
            iq.notify();
        }
    }
}
