package com.estudos.concurrency.conditions;

public class Entregador implements Runnable {
    private ListaMembros listaMembros;

    public Entregador(ListaMembros listaMembros) {
        this.listaMembros = listaMembros;
    }

    @Override
    public void run() {
        String nomeThread = Thread.currentThread().getName();
        System.out.println(nomeThread + " começando o trabalho de entrega");
        int quantidadePendente = listaMembros.getEmailsPendentes();
        boolean aberta = listaMembros.isAberta();
        while (aberta || quantidadePendente > 0) {
            try {
                String email = listaMembros.obterEmailMembro();
                if (email != null) {
                    System.out.println(nomeThread + " Enviando email para " + email);
                    Thread.sleep(2000);
                    System.out.println("Envido para " + email + " concluído com sucesso");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            aberta = listaMembros.isAberta();
            quantidadePendente = listaMembros.getEmailsPendentes();
        }
        System.out.println("Atividades finalizadas.");
    }
}
