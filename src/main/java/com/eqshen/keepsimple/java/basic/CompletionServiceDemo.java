package com.eqshen.keepsimple.java.basic;

import java.util.concurrent.*;

public class CompletionServiceDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(()->{
            Thread.sleep(10000);
            return "test";
        });
        Future<String> future = completionService.poll(3, TimeUnit.SECONDS);
        if(future != null){
            System.out.println(future.get());
        }
        executorService.shutdown();
    }
}
