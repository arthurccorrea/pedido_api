package br.com.prova.pedido_api.controller;

import br.com.prova.pedido_api.enums.StatusPedido;
import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.services.PedidoService;
import br.com.prova.pedido_api.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
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

    @GetMapping("/all/{page}")
    ResponseEntity<List<Pedido>> findAllPaginated(@PathVariable int page, @RequestParam(required = false) StatusPedido status) {
        Page<Pedido> pedidosPage;
        if (status == null) {
            pedidosPage = pedidoService.findAll(PageUtil.getPageable(page));
            return ResponseEntity.ok(pedidosPage.getContent());
        }

        pedidosPage = pedidoService.findAllByStatus(status, PageUtil.getPageable(page));
        return ResponseEntity.ok(pedidosPage.getContent());
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
