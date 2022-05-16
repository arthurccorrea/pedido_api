package br.com.prova.pedido_api.controller;

import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.services.PedidoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pedido-item")
public class PedidoItemController {

    @Autowired
    PedidoItemService pedidoItemService;

    @PostMapping
    ResponseEntity<PedidoItem> save(@RequestBody @Valid PedidoItem pedidoItem) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.save(pedidoItem);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedidoItem.get());
    }

    @PutMapping
    ResponseEntity<PedidoItem> update(@RequestBody @Valid PedidoItem pedidoItem) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.update(pedidoItem);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedidoItem.get());
    }

    @GetMapping("/{id}")
    ResponseEntity<PedidoItem> findById(@PathVariable UUID id) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.findById(id);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedidoItem.get());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<PedidoItem> delete(@PathVariable UUID id) {
        Optional<PedidoItem> opPedidoItem = pedidoItemService.findById(id);
        if (opPedidoItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = pedidoItemService.delete(opPedidoItem.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }

}
