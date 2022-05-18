package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.enums.StatusPedido;
import br.com.prova.pedido_api.enums.TipoItem;
import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.repositories.jpa.PedidoJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    PedidoJPARepository pedidoJPARepository;

    @Autowired
    PedidoItemService pedidoItemService;

    @Autowired
    ItemService itemService;

    public Optional<Pedido> save(Pedido pedido) {
        List<Item> itensPedido = itemService.findItensPedidoItem(pedido.getItens());
        boolean todosItensAtivos = isTodosItensAtivos(itensPedido);
        if(!todosItensAtivos) {
            return Optional.empty();
        }
        if (pedido.getStatusPedido().equals(StatusPedido.ABERTO)) {
            calculaDesconto(pedido, itensPedido);
        }
        Optional<Pedido> opPedido = Optional.ofNullable(pedidoJPARepository.save(pedido));
        if (opPedido.isEmpty()) {
            return opPedido;
        }
        pedido.getItens().stream().forEach((item) -> {
            Pedido pedidoApenasId = new Pedido();
            pedidoApenasId.setId(opPedido.get().getId());
            item.setPedido(pedidoApenasId);
        });
        List<PedidoItem> pedidosItens = pedidoItemService.saveAll(pedido.getItens());
        if (pedidosItens != null && !pedidosItens.isEmpty()) {
            opPedido.get().setItens(pedidosItens);
        }
        return opPedido;
    }

    private void calculaDesconto(Pedido pedido, List<Item> itens) {
        List<Item> itensProduto = itens.stream().filter(item -> item.getTipoItem().equals(TipoItem.PRODUTO)).collect(Collectors.toList());
        if (!itensProduto.isEmpty()) {
            List<PedidoItem> pedidoItensProduto = new ArrayList<>();
            pedido.getItens().stream().forEach(pedidoItem -> {
                Optional<Item> opItem = itensProduto.stream().filter(item -> pedidoItem.getItem().getId().equals(item.getId())).findAny();
                if (opItem.isPresent()) {
                    pedidoItensProduto.add(pedidoItem);
                }
            });
            BigDecimal totalItensProduto = new BigDecimal(
                    pedidoItensProduto
                            .stream()
                            .map(PedidoItem::getValor)
                            .reduce(0.0, Double::sum));
            BigDecimal porcentagemDesconto = new BigDecimal(pedido.getPorcentagemDesconto());
            BigDecimal totalDesconto = porcentagemDesconto.divide(new BigDecimal(100)).multiply(totalItensProduto);
            pedido.setValorTotal(pedido.getValorTotal().subtract(totalDesconto));
        }
    }

    private boolean isTodosItensAtivos(List<Item> itens) {
        Optional<Item> opItem = itens.stream().filter(item -> !item.isAtivo()).findAny();
        return opItem.isEmpty();
    }

    public Optional<Pedido> update(Pedido pedido) {
        return save(pedido);
    }

    public Optional<Pedido> findById(UUID uuid) {
        Optional<Pedido> opPedido = pedidoJPARepository.findById(uuid);
        if(opPedido.isEmpty()) {
            return opPedido;
        }

        List<PedidoItem> pedidoItens = pedidoItemService.findByPedidoId(uuid);
        opPedido.get().setItens(pedidoItens);
        return opPedido;
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoJPARepository.findAll(pageable);
    }

    public Page<Pedido> findAllByStatus(StatusPedido statusPedido, Pageable pageable) {
        return pedidoJPARepository.findByStatusPedidoEquals(statusPedido, pageable);
    }

    public boolean delete(Pedido pedido) {
        pedidoItemService.delete(pedido.getItens());
        pedidoJPARepository.delete(pedido);
        return true;
    }
}
