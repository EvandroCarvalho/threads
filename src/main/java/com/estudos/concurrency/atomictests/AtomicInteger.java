package com.estudos.concurrency.atomictests;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class AtomicInteger implements Runnable {

    public void run() {
        AtomicReference<Integer> counter = new AtomicReference<>(0);

        IntStream.range(0, 10).forEach(value -> {
            counter.set(1 + value);
            System.out.println("Counter value: " + counter + "  Thread " + Thread.currentThread().getName());
        });
    }
}

class ExcuteThread {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);


    public static void main(String[] args) {
        IntStream.range(0, 2).forEach(thread -> {
            executorService.execute(new AtomicInteger());
        });

        executorService.shutdown();
    }
}
