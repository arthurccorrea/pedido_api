package br.com.prova.pedido_api.enums;

public enum StatusPedido {
    ABERTO("Em aberto"), FECHADO("Fechado");

    public String status;

    StatusPedido(String status) {
        this.status = status;
    }
}
