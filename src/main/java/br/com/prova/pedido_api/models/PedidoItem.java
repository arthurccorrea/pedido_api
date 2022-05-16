package br.com.prova.pedido_api.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity(name="pedido_item")
@Table(name="pedido_item")
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PEDIDO_PEDIDO_ITEM"))
    private Pedido pedido;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PEDIDO_ITEM_ITEM"))
    private Item item;
    @NotNull
    @NotBlank
    private String descricao;
    @NotNull
    private String valor;

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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
