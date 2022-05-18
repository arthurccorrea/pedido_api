package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.repositories.jpa.ItemJPARepository;
import br.com.prova.pedido_api.repositories.jpa.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemJPARepository itemJPARepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PedidoItemService pedidoItemService;

    public Optional<Item> save(Item item) {
        return Optional.ofNullable(itemJPARepository.save(item));
    }

    public Optional<Item> update(Item item) {
        return save(item);
    }

    public Optional<Item> findById(UUID uuid) {
        return itemJPARepository.findById(uuid);
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemJPARepository.findAll(pageable);
    }

    public Page<Item> findByDescricao(String descricao, Pageable pageable) {
        return itemJPARepository.findByDescricaoContainsIgnoreCase(descricao, pageable);
    }

    public List<Item> findItensPedidoItem (List<PedidoItem> pedidoItens) {
        Set<UUID> itemIds = pedidoItens.stream().map(p -> p.getItem().getId()).collect(Collectors.toSet());
        List<Item> itens = itemRepository.findByIdIn(itemIds);
        return itens;
    }

    public boolean delete(Item item) {
        List<PedidoItem> pedidosItens = pedidoItemService.findPedidoItemByItemId(item.getId());
        if (!pedidosItens.isEmpty()) {
            return false;
        }
        itemJPARepository.delete(item);
        return true;
    }
}
