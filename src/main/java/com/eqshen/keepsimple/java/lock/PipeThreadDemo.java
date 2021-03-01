package com.eqshen.keepsimple.java.lock;

import lombok.SneakyThrows;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author eqshen
 * @description
 * @date 2021/2/24
 */
public class PipeThreadDemo {
    @SneakyThrows
    public static void main(String[] args) {
        PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter();
        pw.connect(pr);

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("read-thread started");
                char[] buff = new char[1024];
                while (pr.read(buff) != -1){
                    System.out.println(new String(buff));
                }
            }
        }).start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("write-thread started");
                pw.write("hello world,hello java");
                pw.close();
            }
        }).start();

        Thread.sleep(2000);
    }
}
