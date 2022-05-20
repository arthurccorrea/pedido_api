package br.com.prova.pedido_api.enums;

public enum StatusPedido {
    ABERTO("Em aberto"), FECHADO("Fechado");

    public final String status;

    StatusPedido(String status) {
        this.status = status;
    }
}
