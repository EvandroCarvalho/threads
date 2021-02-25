package com.estudos.concurrency.conditions;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Conditional funciona como substitudo para o wait e notify do Object.
 * É importante lembrar que precisamos do lock do objeto
 */
public class ListaMembros {
    private final Queue<String> emails = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition conditionLock = lock.newCondition();
    private boolean aberta = true;

    public int getEmailsPendentes() {
        try {
            lock.lock();
            return this.emails.size();
        } finally {
            lock.unlock();
        }

    }

    public boolean isAberta() {
        return aberta;
    }

    public String obterEmailMembro() {
        String email = null;
        try {
            lock.lock();
            while (this.emails.size() == 0) {
                if (!aberta) return null;
                System.out.println("Lista Vazia, colocando a thread: " + Thread.currentThread().getName() + " em modo wait");
                conditionLock.await();
            }
            email = this.emails.poll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return email;
    }

        public void adicionarEmailMembro (String email){
            try {
                lock.lock();
                this.emails.add(email);
                System.out.println("Email adicionado a lista");
                System.out.println("Notificando as Threads que estão em espera");
                conditionLock.signalAll();
            } finally {
                lock.unlock();
            }

        }

        public void fecharLista () {
            System.out.println("Noticando todas as Threads e fechando a lista " + Thread.currentThread().getName());
            aberta = false;
            try {
                lock.lock();
//                this.emails.notifyAll();
                conditionLock.signalAll();
            } finally {
                lock.unlock();
            }

        }
    }
