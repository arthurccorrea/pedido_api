package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.repositories.jpa.PedidoItemJPARepository;
import br.com.prova.pedido_api.repositories.jpa.PedidoItemRepository;
import br.com.prova.pedido_api.repositories.jpa.PedidoJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoItemService {
    @Autowired
    PedidoItemJPARepository pedidoItemJPARepository;

    @Autowired
    PedidoItemRepository pedidoItemRepository;

    @Autowired
    PedidoJPARepository pedidoJPARepository;

    public Optional<PedidoItem> save(PedidoItem pedidoItem) {
        Optional<Pedido> opPedido = pedidoJPARepository.findById(pedidoItem.getIdPedido());
        if (opPedido.isEmpty()) {
            return Optional.empty();
        }
        pedidoItem.setPedido(opPedido.get());
        return Optional.ofNullable(pedidoItemJPARepository.save(pedidoItem));
    }

    public List<PedidoItem> saveAll(List<PedidoItem> pedidoItens) {
        return pedidoItemJPARepository.saveAll(pedidoItens);
    }

    public Optional<PedidoItem> update(PedidoItem pedidoItem) {
        return save(pedidoItem);
    }

    public Optional<PedidoItem> findById(UUID uuid) {
        Optional<PedidoItem> opPedidoItem = pedidoItemJPARepository.findById(uuid);
        if (opPedidoItem.isEmpty()) {
            return opPedidoItem;
        }

        opPedidoItem.get().setIdPedido(opPedidoItem.get().getPedido().getId());
        return opPedidoItem;
    }

    public List<PedidoItem> findPedidoItemByItemId(UUID itemId) {
        return pedidoItemRepository.findPedidoItemByItemId(itemId);
    }

    public List<PedidoItem> findByPedidoId(UUID pedidoId) {
        return pedidoItemRepository.findPedidoItemByPedidoId(pedidoId);
    }

    public Page<PedidoItem> findAll(Pageable pageable) {
        return pedidoItemJPARepository.findAll(pageable);
    }

    public Page<PedidoItem> findAllByDescricao(String descricao, Pageable pageable) {
        return pedidoItemJPARepository.findByDescricaoContainsIgnoreCase(descricao, pageable);
    }

    public boolean delete(PedidoItem pedidoItem) {
        pedidoItemJPARepository.delete(pedidoItem);
        return true;
    }

    public boolean delete(List<PedidoItem> pedidoItens) {
        pedidoItemJPARepository.deleteAll(pedidoItens);
        return true;
    }
}
