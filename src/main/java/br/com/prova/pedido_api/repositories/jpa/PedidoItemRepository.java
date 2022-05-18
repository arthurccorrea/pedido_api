package br.com.prova.pedido_api.repositories.jpa;

import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.models.QPedidoItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
public class PedidoItemRepository {

    EntityManager em;
    JPAQueryFactory jpaQueryFactory;

    QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    public PedidoItemRepository(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public List<PedidoItem> findPedidoItemByItemId(UUID itemId) {
        JPAQuery<PedidoItem> jpaQuery = new JPAQuery<>(em);
        List<PedidoItem> itens = jpaQuery.select(qPedidoItem).from(qPedidoItem).where(qPedidoItem.item.id.eq(itemId)).fetch();
        return itens;
    }

    public List<PedidoItem> findPedidoItemByPedidoId(UUID pedidoId) {
        JPAQuery<PedidoItem> jpaQuery = new JPAQuery<>(em);
        List<PedidoItem> itens = jpaQuery.select(qPedidoItem).from(qPedidoItem).where(qPedidoItem.pedido.id.eq(pedidoId)).fetch();
        return itens;
    }
}
