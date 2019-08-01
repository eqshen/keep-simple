package com.eqshen.keepsimple.java.thread.leetcode;

import java.util.concurrent.Semaphore;

/**
 * 交替打印tool bar
 *两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 *
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-foobar-alternately
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class AlternatePrint {
    public static void main(String[] args) throws InterruptedException {
        FooBar fooBar = new FooBar(10);

        Thread threadFoo= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadBar = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.bar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("bar");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });




        threadFoo.start();
        threadBar.start();
    }
}


class FooBar {
    private int n;
    private Semaphore fooSem,barSem;

    public FooBar(int n) {
        this.n = n;
        fooSem = new Semaphore(0);
        barSem = new Semaphore(1);

    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.
            barSem.acquire();
            printFoo.run();
            fooSem.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            fooSem.acquire();
            printBar.run();
            barSem.release();
        }
    }
}