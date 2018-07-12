package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    //    private static final Object LOCK = new Object();
    public static final int THREAD_NUMBER = 10000;
    private static int counter;
    private static AtomicInteger atomicCounter = new AtomicInteger();
    private static final Lock lock = new ReentrantLock();

    private static final ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getName() + ", " + thread0.getState());

        final MainConcurrency mc = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREAD_NUMBER);


        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREAD_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.inc();
                    System.out.println(tl.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
        }

/*

        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mc.inc();
                }
                latch.countDown();
            });
            thread.start();
        }

*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(atomicCounter.get());

    }

    private void inc() {
        atomicCounter.incrementAndGet();
    }
}
