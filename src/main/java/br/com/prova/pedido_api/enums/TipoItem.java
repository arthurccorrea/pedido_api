package br.com.prova.pedido_api.enums;

public enum TipoItem {
    PRODUTO("Produto"), SERVICO("Serviço");

    public final String nome;

    TipoItem(String nome) {
        this.nome = nome;
    }
}