package com.company.project.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: chenyin
 * @date: 2019-10-17 20:44
 */
public class Producer {

    static final Object lock = new Object();
    static int num = 1;
    static int max = 100;

    static class EvenThead implements Runnable {
        @Override
        public void run() {
            while (num <= max) {
                synchronized (lock) {
                    if (num % 2 == 0) {
                        System.out.println(num);
                        num++;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    static class OddThead implements Runnable {
        @Override
        public void run() {
            while (num <= max) {
                synchronized (lock) {
                    if (num % 2 == 1) {
                        System.out.println(num);
                        num++;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new EvenThead()).start();

        new Thread(new OddThead()).start();

    }

}
