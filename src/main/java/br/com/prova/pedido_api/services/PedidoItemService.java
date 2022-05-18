package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.repositories.jpa.PedidoItemJPARepository;
import br.com.prova.pedido_api.repositories.jpa.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<PedidoItem> save(PedidoItem pedidoItem) {
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

        opPedidoItem.get().setPedidoId(opPedidoItem.get().getPedido().getId());
        return opPedidoItem;
    }

    public List<PedidoItem> findPedidoItemByItemId(UUID itemId) {
        return pedidoItemRepository.findPedidoItemByItemId(itemId);
    }

    public List<PedidoItem> findByPedidoId(UUID pedidoId) {
        return pedidoItemRepository.findPedidoItemByPedidoId(pedidoId);
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
