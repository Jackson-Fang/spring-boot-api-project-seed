package com.company.project.demo;

/**
 * @author: chenyin
 * @date: 2019-10-17 20:44
 */
public class Producer {
    private static final Object lock = new Object();
    private static int i = 0;

    static class EvenPrinter implements Runnable{
        @Override
        public void run() {
            synchronized (lock) {
                while (i <= 100) {
                    try {
                        if (i % 2 == 1) {
                            lock.wait();
                        } else {
                            System.out.println(Thread.currentThread().getName() + ":" + i);
                            lock.notifyAll();
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    static class OddPrinter implements Runnable{
        @Override
        public void run() {
            synchronized (lock) {
                while (i <= 100) {
                    try {
                        if (i % 2 == 0) {
                            lock.wait();
                        } else {
                            System.out.println(Thread.currentThread().getName() + ":" + i);
                            lock.notifyAll();
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new EvenPrinter()).start();
        new Thread(new OddPrinter()).start();
    }




}
