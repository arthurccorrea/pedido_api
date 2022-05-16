package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public Optional<Pedido> save(Pedido pedido) {
        Optional<Pedido> opPedido = Optional.ofNullable(pedidoRepository.save(pedido));
        if (opPedido.isEmpty()) {
            return opPedido;
        }

        return opPedido;
    }

    public Optional<Pedido> update(Pedido pedido) {
        return save(pedido);
    }

    public Optional<Pedido> findById(UUID uuid) {
        return pedidoRepository.findById(uuid);
    }

    public boolean delete(Pedido pedido) {
        pedidoRepository.delete(pedido);
        return true;
    }
}
