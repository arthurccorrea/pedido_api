package br.com.prova.pedido_api.services;

import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Optional<Item> save(Item item) {
        return Optional.ofNullable(itemRepository.save(item));
    }

    public Optional<Item> update(Item item) {
        return save(item);
    }

    public Optional<Item> findById(UUID uuid) {
        return itemRepository.findById(uuid);
    }

    public boolean delete(Item item) {
        itemRepository.delete(item);
        return true;
    }
}
