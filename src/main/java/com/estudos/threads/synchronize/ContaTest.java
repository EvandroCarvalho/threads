package com.estudos.threads.synchronize;

import java.util.stream.IntStream;


public class ContaTest implements Runnable {
    private final Conta conta = new Conta(); // boa prática sincronizar campo final

    public static void main(String[] args) throws InterruptedException {
        ContaTest contaTest = new ContaTest();
        Thread thread = new Thread(contaTest, "Luffy");
        Thread thread2 = new Thread(contaTest, "Sanji");
        Thread thread3 = new Thread(contaTest, "Midoirya");
        Thread thread4 = new Thread(contaTest, "Bakugo");
        thread.start();
        thread2.start();
        thread3.start();
        thread3.join();
        thread4.start();

        System.out.println("######acabou");
    }

    /**
     * quando métodos estáticos são sicronizados, é dado o lock na classe toda
     */
    private static void imprimir() {
        synchronized (ContaTest.class) {
            System.out.println("imprimindo");
        }
    }

    /**
     * O sincronismo é possivel por conta do lock
     * Cada object possui apenas um lock
     * Uma Thread pode pegar mais de um lock
     */
    private synchronized void saque(int valor) {
//        synchronized (conta) { sincronismo em block
        if (conta.getSaldo() >= valor) {
            System.out.println(Thread.currentThread().getName() + " está indo sacar");
            conta.saque(valor);
            System.out.println(Thread.currentThread().getName() + " completou o saque, saldo: " + conta.getSaldo());
        } else {
            System.out.println("Sem dinheiro para " + Thread.currentThread().getName() + " efeturar o saque, saldo " + conta.getSaldo());
        }
//        }

    }

    @Override
    public void run() {
        IntStream.range(0, 5).forEach(valor -> {
            saque(10);
            if (conta.getSaldo() < 0) {
                System.out.println("oh God!");
            }
        });
    }
}
