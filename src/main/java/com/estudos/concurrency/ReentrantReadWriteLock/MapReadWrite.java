package com.estudos.concurrency.ReentrantReadWriteLock;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class MapReadWrite {
    private final Map<String, String> map = new LinkedHashMap<>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public Object[] allKeys() {
        return map.keySet().toArray();
    }
}

class MapReadWriteLock {
    private static final MapReadWrite mapReadWrite = new MapReadWrite();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(new Write());
        Thread thread1 = new Thread(new ReadA());
        Thread thread2 = new Thread(new ReadB());
        thread0.start();
//        thread0.join();
        thread1.start();
        thread2.start();
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            IntStream.range(0, 100).forEach(i -> {
                rwl.writeLock().lock();
                try {
                    mapReadWrite.put(String.valueOf(i), String.valueOf(i));
                } finally {
                    rwl.writeLock().unlock();
                }
            });
        }
    }

    static class ReadA implements Runnable {
        @Override
        public void run() {
            try {
                IntStream.range(0, 10).forEach(i -> {
                    rwl.readLock().lock();
                    System.out.println(Thread.currentThread().getName() + " " + Arrays.toString(mapReadWrite.allKeys()));
                });
            } finally {
                rwl.readLock().unlock();
            }
        }
    }

    static class ReadB implements Runnable {
        @Override
        public void run() {
            try {
                IntStream.range(0, 10).forEach(i -> {
                    rwl.readLock().lock();
                    System.out.println(Thread.currentThread().getName() + " " + Arrays.toString(mapReadWrite.allKeys()));
                });
            } finally {
                rwl.readLock().unlock();
            }
        }
    }
}
