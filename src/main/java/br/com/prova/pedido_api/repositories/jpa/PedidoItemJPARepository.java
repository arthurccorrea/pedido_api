package br.com.prova.pedido_api.repositories.jpa;

import br.com.prova.pedido_api.models.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoItemJPARepository extends JpaRepository<PedidoItem, UUID> {

    List<PedidoItem> findByPedido_Id(@NonNull UUID id);

}
