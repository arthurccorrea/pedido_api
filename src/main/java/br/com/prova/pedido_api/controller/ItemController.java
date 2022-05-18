package br.com.prova.pedido_api.controller;

import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.services.ItemService;
import br.com.prova.pedido_api.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

    @GetMapping("/all/{page}")
    ResponseEntity<List<Item>> findAllPaginated(@PathVariable int page, @RequestParam(required = false) String descricao) {
        Page<Item> itensPage;
        if (descricao == null) {
            itensPage = itemService.findAll(PageUtil.getPageable(page));
            return ResponseEntity.ok(itensPage.getContent());
        }

        itensPage = itemService.findByDescricao(descricao, PageUtil.getPageable(page));
        return ResponseEntity.ok(itensPage.getContent());
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
