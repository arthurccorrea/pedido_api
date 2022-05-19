package br.com.prova.pedido_api.util;

import br.com.prova.pedido_api.enums.StatusPedido;
import br.com.prova.pedido_api.enums.TipoItem;
import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.models.PedidoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtil {

    public static Item getItem(TipoItem tipoItem, boolean ativo) {
        Item item = new Item();
        item.setId(new UUID(1, 2));
        item.setTipoItem(tipoItem);
        item.setDescricao("Item teste");
        item.setValor(BigDecimal.valueOf(10));
        item.setAtivo(ativo);
        return item;
    }

    public static List<Item> getItemList(int listSize) {
        List<Item> itens = new ArrayList<>();
        for(int i = 0 ; i < listSize ; i++) {
            itens.add(getItem(TipoItem.PRODUTO, true));
        }

        return itens;
    }

    public static List<Item> getItemInativoList(int listSize) {
        List<Item> itens = new ArrayList<>();
        for(int i = 0 ; i < listSize ; i++) {
            itens.add(getItem(TipoItem.PRODUTO, false));
        }

        return itens;
    }

    public static Page<Item> getItemPage(int listSize, int page) {
        Pageable pageable = PageRequest.of(page, listSize);
        return new PageImpl<>(getItemList(listSize), pageable, listSize);
    }

    public static Pedido getPedido(int quantidadeItens) {
        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.ABERTO);
        pedido.setNomeComprador("Comprador de teste");
        pedido.setPorcentagemDesconto(5.0);
        pedido.setId(new UUID (2, 1));
        pedido.setItens(getPedidoItemList(quantidadeItens));
        return pedido;
    }

    public static List<Pedido> getPedidoList(int listSize) {
        List<Pedido> pedidoList = new ArrayList<>();
        for(int i = 0 ; i < listSize ; i++) {
            pedidoList.add(getPedido(2));
        }

        return pedidoList;
    }

    public static Page<Pedido> getPedidoPage(int listSize, int page) {
        Pageable pageable = PageRequest.of(page, listSize);
        return new PageImpl<>(getPedidoList(listSize), pageable, listSize);
    }

    public static PedidoItem getPedidoItem() {
        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setPedido(getPedido(0));
        pedidoItem.setItem(getItem(TipoItem.PRODUTO, true));
        pedidoItem.setDescricao("Descricao PedidoItem teste");
        pedidoItem.setValor(10.0);
        pedidoItem.setQuantidade(2);
        pedidoItem.setId(new UUID(3, 1));
        return pedidoItem;
    }

    public static List<PedidoItem> getPedidoItemList(int listSize) {
        List<PedidoItem> pedidoItens = new ArrayList<>();
        for(int i = 0 ; i < listSize ; i++) {
            pedidoItens.add(getPedidoItem());
        }

        return pedidoItens;
    }

    public static Page<PedidoItem> getPedidoItemPage(int listSize, int page) {
        Pageable pageable = PageRequest.of(page, listSize);
        return new PageImpl<>(getPedidoItemList(listSize), pageable, listSize);
    }
}
