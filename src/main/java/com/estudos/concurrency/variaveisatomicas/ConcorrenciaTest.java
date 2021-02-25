package com.estudos.concurrency.variaveisatomicas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

class Contador {
    private int count;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
            atomicInteger.getAndIncrement();
        } finally {
            lock.unlock();
        }
    }

    public int getAtomicInteger() {
        return atomicInteger.intValue();
    }

    public int getCount() {
        return count;
    }
}

class IncrementadorThread extends Thread {
    private final Contador contador;

    public IncrementadorThread(Contador contador) {
        System.out.println("Executando thread" + Thread.currentThread().getName());
        this.contador = contador;
    }

    @Override
    public void run() {
        IntStream.range(0, 10000).forEach(numero -> contador.increment());
    }
}

public class ConcorrenciaTest {
    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();
        IncrementadorThread thread1 = new IncrementadorThread(contador);
        IncrementadorThread thread2 = new IncrementadorThread(contador);
        IncrementadorThread thread3 = new IncrementadorThread(contador);
        thread1.start();
        thread2.start();
        thread3.start();
//        thread1.join();
        thread3.join();
        System.out.println(contador.getCount());
        System.out.println("Variável atomica: " + contador.getAtomicInteger());

    }
}
