package com.eqshen.keepsimple.java.lock;

import java.util.ArrayList;
import java.util.List;

public class VolatileDemo {

    private List<Integer> list = new ArrayList<>();

    private boolean flag = true;

    public void play()  {
        new Thread(() -> {
            System.out.println("++++++ start playing");
            while (list.size() == 0){
//                System.out.println("list size is :"+ list.size());
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println("======== play over");
        }).start();
    }

    public void replaceList(){
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        list = tmp;
        System.out.println("replace done");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.play();
        Thread.sleep(201);

        Thread thread = new Thread(()->{
            volatileDemo.replaceList();
        });
        thread.start();

        thread.join();

    }
}
