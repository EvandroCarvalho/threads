package com.estudos.collectionsthreadsafe;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CopyOnWriteTest implements Runnable {
    private final List<Integer> list = new CopyOnWriteArrayList<>();

    public CopyOnWriteTest() {
        IntStream.range(0, 9000).forEach(list::add);
    }

    @Override
    public void run() {
        // O iterador mantém o estado do momento em que é pego a lista
        Iterator<Integer> iterator = list.iterator();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (iterator.hasNext()) {
            System.out.println(Thread.currentThread().getName() + " " + iterator.next());
        }
    }

    public List<Integer> getList() {
        return list;
    }

    public static void main(String[] args) {
        CopyOnWriteTest copyOnWriteTest = new CopyOnWriteTest();
        Thread t1 = new Thread(copyOnWriteTest);
        Thread t2 = new Thread(copyOnWriteTest);
        Thread removedor = new Thread(new RemoverMembros(copyOnWriteTest.getList()));

        t1.start();
        t2.start();
        removedor.start();
    }

    private static class RemoverMembros implements Runnable {
        private final List<Integer> list;

        public RemoverMembros(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            IntStream.range(0, 500).forEach(i -> System.out.println(Thread.currentThread().getName() + " removeu: " + list.remove(i)));
        }
    }
}
