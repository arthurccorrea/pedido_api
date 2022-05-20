package br.com.prova.pedido_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name="pedido_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pedido"})
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "FK_PEDIDO_PEDIDO_ITEM"), nullable = true)
    private Pedido pedido;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PEDIDO_ITEM_ITEM"))
    private Item item;
    @NotNull
    @NotBlank
    private String descricao;
    @NotNull
    private double valor;
    @NotNull
    private double quantidade;
    @Transient
    private UUID idPedido;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
