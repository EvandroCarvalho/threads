package com.estudos.threads;

import java.util.stream.IntStream;

/**
 * Daemon X User
 * JVM só finaliza sua execução quando as Threas User são executadas
 * Threads Daemon podem ser paradas pela JVM, ou seja, a JVM não se importa de parar sua execução
 * quando houver threads Daemon ativas
 * GC é um exemplo de Thread Daemon
 */


/**
 * Não é uma boa prática extender caso não for mudar a implementação do método
 */
class ThreadExemplo extends Thread {
    private final char c;
    public ThreadExemplo(char c) {
        this.c = c;
    }

    @Override
    public void run() {
        System.out.println("Thread executando: " + Thread.currentThread().getName());
        IntStream.range(1, 100).forEach( value -> {
            System.out.print(c);
            if(value % 100 == 0) {
                System.out.println();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        });
    }
}

/**
 * Mehor prática - implementar
 */
class ThreadExemploRunnable implements Runnable {
    private final char c;
    public ThreadExemploRunnable(char c) {
        this.c = c;
    }
    @Override
    public void run() {
        System.out.println("Thread executando: " + Thread.currentThread().getName());
        IntStream.range(1, 100).forEach( value -> {
            System.out.print(c);
            if(value % 100 == 0) {
                System.out.println();
            }
        });
    }
}

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
//        new ThreadExemplo('A').start();
//        new ThreadExemplo('B').start();
//        new ThreadExemplo('C').start();
//        new ThreadExemplo('D').start();
//        new ThreadExemplo('E').start();
        final Thread thread0 = new Thread(new ThreadExemploRunnable('A') ,"t0");
        final Thread thread1 = new Thread(new ThreadExemploRunnable('B') ,"t1");
        final Thread thread3 = new Thread(new ThreadExemploRunnable('C') ,"t2");
        final Thread thread4 = new Thread(new ThreadExemploRunnable('D') ,"t3");
        final Thread thread2 = new Thread(new ThreadExemploRunnable('E') ,"t4");

        thread0.start();
        thread0.setPriority(Thread.MAX_PRIORITY);
        thread0.join();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
