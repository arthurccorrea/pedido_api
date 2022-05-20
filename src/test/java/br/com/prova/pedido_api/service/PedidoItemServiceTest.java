package br.com.prova.pedido_api.service;

import br.com.prova.pedido_api.models.PedidoItem;
import br.com.prova.pedido_api.repositories.jpa.PedidoItemJPARepository;
import br.com.prova.pedido_api.repositories.jpa.PedidoItemRepository;
import br.com.prova.pedido_api.repositories.jpa.PedidoJPARepository;
import br.com.prova.pedido_api.services.PedidoItemService;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PedidoItemServiceTest {

    @MockBean
    PedidoItemJPARepository pedidoItemJPARepository;

    @MockBean
    PedidoItemRepository pedidoItemRepository;

    @MockBean
    PedidoJPARepository pedidoJPARepository;

    @Autowired
    PedidoItemService pedidoItemService;

    @Test
    public void dadoPedidoItemExistente_deveRetornarOptionalPresent() {
        UUID uuid = new UUID(3, 1);
        // Given
        when(pedidoItemJPARepository.findById(any())).thenReturn(Optional.of(TestUtil.getPedidoItem()));

        // When
        Optional<PedidoItem> opPedidoItem = pedidoItemService.findById(uuid);

        // Then
        verify(pedidoItemJPARepository).findById(any());
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertTrue(opPedidoItem.isPresent());
    }

    @Test
    public void dadoPedidoItemInexistente_deveRetornarOptionalEmpty() {
        UUID uuid = new UUID(3, 2);
        // Given
        when(pedidoItemJPARepository.findById(any())).thenReturn(Optional.empty());

        // When
        Optional<PedidoItem> opPedidoItem = pedidoItemService.findById(uuid);

        // Then
        verify(pedidoItemJPARepository).findById(any());
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertTrue(opPedidoItem.isEmpty());
    }

    @Test
    public void dadoPedidoItem_deveSalvar() {
        // Given
        when(pedidoJPARepository.findById(any())).thenReturn(Optional.of(TestUtil.getPedido(0)));
        when(pedidoItemJPARepository.save(any())).thenReturn(TestUtil.getPedidoItem());

        PedidoItem pedidoItem = TestUtil.getPedidoItem();
        pedidoItem.setId(null);

        // When
        Optional<PedidoItem> opPedidoItem = pedidoItemService.save(pedidoItem);

        //Then
        verify(pedidoItemJPARepository).save(pedidoItem);
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertTrue(opPedidoItem.isPresent());
    }

    @Test
    public void dadoPedidoItemList_deveSalvarTodos() {
        // Given
        when(pedidoItemJPARepository.saveAll(anyList())).thenReturn(TestUtil.getPedidoItemList(3));

        List<PedidoItem> pedidoItemList = TestUtil.getPedidoItemList(3);
        pedidoItemList.forEach(item -> item.setId(null));

        // When
        List<PedidoItem> pedidoItemListRetorno = pedidoItemService.saveAll(pedidoItemList);

        //Then
        verify(pedidoItemJPARepository).saveAll(anyList());
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertFalse(pedidoItemListRetorno.isEmpty());
    }

    @Test
    public void dadoFindAll_deveRetonarTodosOsPedidoItem() {
        Pageable pageable = PageRequest.of(0, 20);
        // Given
        when(pedidoItemJPARepository.findAll(pageable)).thenReturn(TestUtil.getPedidoItemPage(20, 0));

        // When
        Page<PedidoItem> pedidoItemPage = pedidoItemService.findAll(pageable);

        // Then
        verify(pedidoItemJPARepository).findAll(pageable);
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertNotNull(pedidoItemPage);
        assertFalse(pedidoItemPage.getContent().isEmpty());
    }

    @Test
    public void dadoFindAllByDescricao_deveRetonarPagePedidoItem() {
        Pageable pageable = PageRequest.of(0, 20);
        String descricaoPesquisa = "Teste";
        // Given
        when(pedidoItemJPARepository.findByDescricaoContainsIgnoreCase(descricaoPesquisa, pageable)).thenReturn(TestUtil.getPedidoItemPage(2, 0));

        // When
        Page<PedidoItem> pedidoItemPage = pedidoItemService.findAllByDescricao(descricaoPesquisa, pageable);

        // Then
        verify(pedidoItemJPARepository).findByDescricaoContainsIgnoreCase(descricaoPesquisa, pageable);
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertNotNull(pedidoItemPage);
        assertFalse(pedidoItemPage.getContent().isEmpty());
    }

    @Test
    public void dadoItemId_deveRetornarListaPedidoItem() {
        UUID itemId = new UUID(1, 2);

        // Given
        when(pedidoItemRepository.findPedidoItemByItemId(any())).thenReturn(TestUtil.getPedidoItemList(5));

        // When
        List<PedidoItem> pedidoItemList = pedidoItemService.findPedidoItemByItemId(itemId);

        // Then
        verify(pedidoItemRepository).findPedidoItemByItemId(itemId);
        verifyNoMoreInteractions(pedidoItemRepository);
        assertFalse(pedidoItemList.isEmpty());
    }

    @Test
    public void dadoPedidoId_deveRetornarListaPedidoItem() {
        UUID pedidoId = new UUID(1, 2);

        // Given
        when(pedidoItemRepository.findPedidoItemByPedidoId(any())).thenReturn(TestUtil.getPedidoItemList(2));

        // When
        List<PedidoItem> pedidoItemList = pedidoItemService.findByPedidoId(pedidoId);

        // Then
        verify(pedidoItemRepository).findPedidoItemByPedidoId(any());
        verifyNoMoreInteractions(pedidoItemRepository);
        assertFalse(pedidoItemList.isEmpty());
    }

    @Test
    public void dadoPedidoItemExistente_deveExcluir() {
        // Given
        doNothing().when(pedidoItemJPARepository).delete(any());

        // When
        boolean excluido = pedidoItemService.delete(TestUtil.getPedidoItem());

        verify(pedidoItemJPARepository).delete(any());
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertTrue(excluido);
    }

    @Test
    public void dadoPedidoItemListExistente_deveExcluirTodos() {
        // Given
        doNothing().when(pedidoItemJPARepository).deleteAll(anyList());

        // When
        boolean excluido = pedidoItemService.delete(TestUtil.getPedidoItemList(2));

        verify(pedidoItemJPARepository).deleteAll(anyList());
        verifyNoMoreInteractions(pedidoItemJPARepository);
        assertTrue(excluido);
    }
}
