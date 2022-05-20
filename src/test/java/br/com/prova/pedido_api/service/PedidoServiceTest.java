package br.com.prova.pedido_api.service;

import br.com.prova.pedido_api.enums.StatusPedido;
import br.com.prova.pedido_api.models.Pedido;
import br.com.prova.pedido_api.repositories.jpa.PedidoJPARepository;
import br.com.prova.pedido_api.services.ItemService;
import br.com.prova.pedido_api.services.PedidoItemService;
import br.com.prova.pedido_api.services.PedidoService;
import br.com.prova.pedido_api.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PedidoServiceTest {

    @MockBean
    PedidoJPARepository pedidoJPARepository;

    @MockBean
    PedidoItemService pedidoItemService;

    @MockBean
    ItemService itemService;

    @Autowired
    PedidoService pedidoService;

    @Test
    public void dadoPedidoExistente_deveRetonarOptionalPresent() {
        UUID uuid = new UUID(1, 2);
        // Given
        when(pedidoJPARepository.findById(any())).thenReturn(Optional.of(TestUtil.getPedido(2)));
        when(pedidoItemService.findByPedidoId(any())).thenReturn(TestUtil.getPedidoItemList(2));

        // When
        Optional<Pedido> opPedido = pedidoService.findById(uuid);

        // Then
        verify(pedidoJPARepository).findById(any());
        verify(pedidoItemService).findByPedidoId(any());
        verifyNoMoreInteractions(pedidoJPARepository);
        verifyNoMoreInteractions(pedidoItemService);
        assertTrue(opPedido.isPresent());
    }

    @Test
    public void dadoPedidoInexistente_deveRetornarOptionalEmpty() {
        UUID uuid = new UUID(1, 3);
        // Given
        when(pedidoJPARepository.findById(any())).thenReturn(Optional.empty());

        // When
        Optional<Pedido> opPedido = pedidoService.findById(uuid);

        // Then
        verify(pedidoJPARepository).findById(any());
        verifyNoMoreInteractions(pedidoJPARepository);
        verifyNoInteractions(pedidoItemService);
        assertTrue(opPedido.isEmpty());
    }

    @Test
    public void dadoPedidoValido_deveSalvar() {
        // Given
        when(itemService.findItensPedidoItem(any())).thenReturn(TestUtil.getItemList(2));
        when(pedidoJPARepository.save(any())).thenReturn(TestUtil.getPedido(2));
        when(pedidoItemService.saveAll(any())).thenReturn(TestUtil.getPedidoItemList(2));

        Pedido pedido = TestUtil.getPedido(2);
        pedido.setId(null);

        // When
        Optional<Pedido> opPedido = pedidoService.save(pedido);

        //Then
        verify(itemService).findItensPedidoItem(any());
        verify(pedidoJPARepository).save(any());
        verify(pedidoItemService).saveAll(any());
        verifyNoMoreInteractions(itemService);
        verifyNoMoreInteractions(pedidoJPARepository);
        verifyNoMoreInteractions(pedidoItemService);
        assertTrue(opPedido.isPresent());
    }

    @Test
    public void dadoPedidoComItemInativo_naoDeveSalvar() {
        // Given
        when(itemService.findItensPedidoItem(any())).thenReturn(TestUtil.getItemInativoList(2));

        Pedido pedido = TestUtil.getPedido(2);
        pedido.setId(null);

        // When
        Optional<Pedido> opPedido = pedidoService.save(pedido);

        //Then
        verify(itemService).findItensPedidoItem(any());
        verifyNoInteractions(pedidoJPARepository);
        verifyNoInteractions(pedidoItemService);
        assertTrue(opPedido.isEmpty());
    }

    @Test
    public void dadoFindAll_deveRetornarTodosOsPedidos() {
        Pageable pageable = PageRequest.of(0, 20);
        // Given
        when(pedidoJPARepository.findAll(pageable)).thenReturn(TestUtil.getPedidoPage(20, 0));

        // When
        Page<Pedido> pedidoPage = pedidoService.findAll(pageable);

        // Then
        verify(pedidoJPARepository).findAll(pageable);
        verifyNoMoreInteractions(pedidoJPARepository);
        assertNotNull(pedidoPage);
        assertFalse(pedidoPage.getContent().isEmpty());
    }

    @Test
    public void dadoStatusAberto_deveRetornarPedidosStatusAberto() {
        Pageable pageable = PageRequest.of(0, 5);
        StatusPedido statusPesquisa = StatusPedido.ABERTO;
        // Given
        when(pedidoJPARepository.findByStatusPedidoEquals(statusPesquisa, pageable)).thenReturn(TestUtil.getPedidoPage(5, 0));

        // When
        Page<Pedido> pedidoPage = pedidoService.findAllByStatus(statusPesquisa, pageable);

        // Then
        verify(pedidoJPARepository).findByStatusPedidoEquals(statusPesquisa, pageable);
        verifyNoMoreInteractions(pedidoJPARepository);
        assertNotNull(pedidoPage);
        assertFalse(pedidoPage.getContent().isEmpty());
    }

    @Test
    public void dadoPedidoExistente_deveExcluir() {
        // Given
        when(pedidoItemService.delete(anyList())).thenReturn(true);
        doNothing().when(pedidoJPARepository).delete(any());

        // When
        boolean excluido = pedidoService.delete(TestUtil.getPedido(2));

        verify(pedidoJPARepository).delete(any());
        verify(pedidoItemService).delete(anyList());
        verifyNoMoreInteractions(pedidoJPARepository);
        verifyNoMoreInteractions(pedidoItemService);
        assertTrue(excluido);
    }

}
