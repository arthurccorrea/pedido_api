package br.com.prova.pedido_api.repositories.jpa;

import br.com.prova.pedido_api.models.Item;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import br.com.prova.pedido_api.models.QItem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class ItemRepository {

    final EntityManager em;

    final QItem qItem = QItem.item;

    public ItemRepository(EntityManager em) {
        this.em = em;
    }

    public List<Item> findByIdIn(Set<UUID> ids) {
        JPAQuery<Item> jpaQuery = new JPAQuery<>(em);
        return jpaQuery.select(qItem).from(qItem).where(qItem.id.in(ids)).fetch();
    }
}
