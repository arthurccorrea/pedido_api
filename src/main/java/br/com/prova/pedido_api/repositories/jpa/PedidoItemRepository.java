package br.com.prova.pedido_api.repositories.jpa;

import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.models.QPedidoItem;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
public class PedidoItemRepository {

    final EntityManager em;

    final QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    public PedidoItemRepository(EntityManager em) {
        this.em = em;
    }

    public List<PedidoItem> findPedidoItemByItemId(UUID itemId) {
        JPAQuery<PedidoItem> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(qPedidoItem).from(qPedidoItem).where(qPedidoItem.item.id.eq(itemId)).fetch();
    }

    public List<PedidoItem> findPedidoItemByPedidoId(UUID pedidoId) {
        JPAQuery<PedidoItem> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(qPedidoItem).from(qPedidoItem).where(qPedidoItem.pedido.id.eq(pedidoId)).fetch();
    }
}
