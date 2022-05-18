package br.com.prova.pedido_api.repositories.jpa;

import br.com.prova.pedido_api.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemJPARepository extends JpaRepository<Item, UUID> {

    Page<Item> findByDescricaoContainsIgnoreCase(@NonNull String descricao, Pageable pageable);
}
