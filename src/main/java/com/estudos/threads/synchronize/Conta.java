package com.estudos.threads.synchronize;

public class Conta {
    private int saldo = 50;

    public int getSaldo() {
        return saldo;
    }

    public void saque(int saque) {
        saldo = saldo - saque;
    }
}
