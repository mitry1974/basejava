package ru.javawebinar.basejava;

public class Main11 {
    public static void main(String[] args) {

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(() -> lock(lock2, lock1));

        Thread thread2 = new Thread(() -> lock(lock1, lock2));

        thread1.start();
        thread2.start();

/*
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }

    private static void lock(Object lock1, Object lock2) {
        synchronized (lock2) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println("Thread2, lock 1");
            }
        }
    }
}
