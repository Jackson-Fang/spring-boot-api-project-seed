package com.company.project.demo;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: chenyin
 */
public class FutureDemo {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        long begin = System.currentTimeMillis();

        List<Future> futureList = Lists.newArrayListWithCapacity(10);
        for (int i = 0; i < 10; i++) {
            final int sleepSeconds = i;
            final int taskNo = i;

            Future future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    try {
                        Thread.sleep(sleepSeconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("任务:" + taskNo + ",耗时:" + (System.currentTimeMillis() - start));
                    }
                }
            });
            futureList.add(future);
        }
        futureList.forEach(t -> {
            try {
                t.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        System.out.println("所有任务总耗时：" + (System.currentTimeMillis() - begin));

    }
}
