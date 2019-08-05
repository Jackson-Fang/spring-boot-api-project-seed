package com.company.project.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author: chenyin
 * @date: 2019-08-04 15:29
 */
public class CompletableFutureDemo {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        List<Integer> taskList = new ArrayList<>();
        taskList.add(1);
        taskList.add(2);
        taskList.add(3);

        List<CompletableFuture<Integer>> futureList = taskList.stream().map(t ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return t + 1;
                }, executorService)
                        //结果转换
                        .thenApply(i-> i + 1)
                        .exceptionally(e->{
                            e.printStackTrace();
                            return 0;
                        })
                        .whenComplete((r, e)-> System.out.println(r))
        ).collect(Collectors.toList());


        //同步等待
        List<Integer> resultList = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(resultList));


    }
}
