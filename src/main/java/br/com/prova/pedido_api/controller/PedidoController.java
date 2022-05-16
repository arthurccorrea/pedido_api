package br.com.prova.pedido_api.controller;

import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping
    ResponseEntity<Pedido> save(@RequestBody @Valid Pedido pedido) {
        Optional<Pedido> opPedido = pedidoService.save(pedido);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedido.get());
    }

    @PutMapping
    ResponseEntity<Pedido> update(@RequestBody @Valid Pedido pedido) {
        Optional<Pedido> opPedido = pedidoService.update(pedido);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedido.get());
    }

    @GetMapping("/{id}")
    ResponseEntity<Pedido> findById(@PathVariable UUID id) {
        Optional<Pedido> opPedido = pedidoService.findById(id);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(opPedido.get());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Pedido> delete(@PathVariable UUID id) {
        Optional<Pedido> opPedido = pedidoService.findById(id);
        if (opPedido.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = pedidoService.delete(opPedido.get());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }

}
