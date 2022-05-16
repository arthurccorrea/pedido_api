package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.repositories.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoItemService {
    @Autowired
    PedidoItemRepository pedidoItemRepository;

    public Optional<PedidoItem> save(PedidoItem pedidoItem) {
        return Optional.ofNullable(pedidoItemRepository.save(pedidoItem));
    }

    public List<PedidoItem> saveAll(List<PedidoItem> pedidoItens) {
        return pedidoItemRepository.saveAll(pedidoItens);
    }

    public Optional<PedidoItem> update(PedidoItem pedidoItem) {
        return save(pedidoItem);
    }

    public Optional<PedidoItem> findById(UUID uuid) {
        return pedidoItemRepository.findById(uuid);
    }

    public boolean delete(PedidoItem pedidoItem) {
        pedidoItemRepository.delete(pedidoItem);
        return true;
    }
}
