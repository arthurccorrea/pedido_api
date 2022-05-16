package br.com.prova.pedido_api.controller;

import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.services.ItemService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping
    ResponseEntity<Item> save(@RequestBody @Valid Item item) {
        Optional<Item> opItem = itemService.save(item);
        if (opItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opItem.get());
    }

    @PutMapping
    ResponseEntity<Item> update(@RequestBody @Valid Item item) {
        Optional<Item> opItem = itemService.update(item);
        if (opItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opItem.get());
    }

    @GetMapping("/{id}")
    ResponseEntity<Item> findById(@PathVariable UUID id) {
        Optional<Item> opItem = itemService.findById(id);
        if (opItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opItem.get());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Item> delete(@PathVariable UUID id) {
        Optional<Item> opItem = itemService.findById(id);
        if (opItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = itemService.delete(opItem.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
