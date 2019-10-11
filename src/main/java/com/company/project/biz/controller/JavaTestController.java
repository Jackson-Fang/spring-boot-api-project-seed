package com.company.project.biz.controller;


/**
 * @author: chenyin
 * @date: 2019-09-16 14:46
 */
public class JavaTestController  {

    public synchronized void test() {
        System.out.println("1");
    }
    public static synchronized void test1() {
        System.out.println("1");
    }
    public void test2() {
        synchronized (new Object()) {
            System.out.println(1);
        }
    }

}
