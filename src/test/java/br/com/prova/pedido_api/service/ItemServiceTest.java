package br.com.prova.pedido_api.service;

import br.com.prova.pedido_api.enums.TipoItem;
import br.com.prova.pedido_api.models.Item;
import br.com.prova.pedido_api.repositories.jpa.ItemJPARepository;
import br.com.prova.pedido_api.repositories.jpa.ItemRepository;
import br.com.prova.pedido_api.services.ItemService;
import br.com.prova.pedido_api.services.PedidoItemService;
import br.com.prova.pedido_api.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ItemServiceTest {

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    ItemJPARepository itemJPARepository;

    @MockBean
    PedidoItemService pedidoItemService;

    @Autowired
    ItemService itemService;

    @Test
    public void dadoItemExistente_deveRetornarOptionalPresent() {
        UUID uuid = new UUID(1, 2);
        // Given
        when(itemJPARepository.findById(any())).thenReturn(Optional.of(TestUtil.getItem(TipoItem.PRODUTO, true)));

        // When
        Optional<Item> opItem = itemService.findById(uuid);

        // Then
        verify(itemJPARepository).findById(any());
        verifyNoMoreInteractions(itemJPARepository);
        assertTrue(opItem.isPresent());
    }

    @Test
    public void dadoItemInexistente_deveRetornarOptionalEmpty() {
        UUID uuid = new UUID(1, 3);
        // Given
        when(itemJPARepository.findById(any())).thenReturn(Optional.empty());

        // When
        Optional<Item> opItem = itemService.findById(uuid);

        // Then
        verify(itemJPARepository).findById(any());
        verifyNoMoreInteractions(itemJPARepository);
        assertTrue(opItem.isEmpty());
    }

    @Test
    public void dadoItemValido_deveSalvar() {
        // Given
        when(itemJPARepository.save(any())).thenReturn(TestUtil.getItem(TipoItem.PRODUTO, true));

        Item item = TestUtil.getItem(TipoItem.PRODUTO, true);
        item.setId(null);

        // When
        Optional<Item> opItem = itemService.save(item);

        //Then
        verify(itemJPARepository).save(item);
        verifyNoMoreInteractions(itemJPARepository);
        assertTrue(opItem.isPresent());
    }

    @Test
    public void dadoFindAll_deveRetonarTodosOsItens() {
        Pageable pageable = PageRequest.of(0, 20);
        // Given
        when(itemJPARepository.findAll(pageable)).thenReturn(TestUtil.getItemPage(20, 0));

        // When
        Page<Item> itemPage = itemService.findAll(pageable);

        // Then
        verify(itemJPARepository).findAll(pageable);
        verifyNoMoreInteractions(itemJPARepository);
        assertNotNull(itemPage);
        assertFalse(itemPage.getContent().isEmpty());
    }

    @Test
    public void dadoDescricaoExistente_deveRetornarPageItem() {
        Pageable pageable = PageRequest.of(0, 20);
        String descricaoPesquisa = "Teste";
        // Given
        when(itemJPARepository.findByDescricaoContainsIgnoreCase(descricaoPesquisa, pageable)).thenReturn(TestUtil.getItemPage(2, 0));

        // When
        Page<Item> itemPage = itemService.findByDescricao(descricaoPesquisa, pageable);

        // Then
        verify(itemJPARepository).findByDescricaoContainsIgnoreCase(descricaoPesquisa, pageable);
        verifyNoMoreInteractions(itemJPARepository);
        assertNotNull(itemPage);
        assertFalse(itemPage.getContent().isEmpty());
    }

    @Test
    public void dadoDescricaoInexistente_deveRetonarPageVazia() {
        Pageable pageable = PageRequest.of(0, 20);
        String descricaoPesquisa = "Teste";
        Page<Item> pageRetorno = new PageImpl<>(new ArrayList<>(), pageable, 0);
        // Given
        when(itemJPARepository.findByDescricaoContainsIgnoreCase(descricaoPesquisa, pageable)).thenReturn(pageRetorno);

        // When
        Page<Item> itemPage = itemService.findByDescricao(descricaoPesquisa, pageable);

        // Then
        verify(itemJPARepository).findByDescricaoContainsIgnoreCase(descricaoPesquisa, pageable);
        verifyNoMoreInteractions(itemJPARepository);
        assertNotNull(itemPage);
        assertTrue(itemPage.getContent().isEmpty());
    }

    @Test
    public void dadoListaPedidoItem_deveRetornarListaItem() {
        // Given
        when(itemRepository.findByIdIn(any())).thenReturn(TestUtil.getItemList(5));

        // When
        List<Item> itemList = itemService.findItensPedidoItem(TestUtil.getPedidoItemList(5));

        // Then
        verify(itemRepository).findByIdIn(any());
        verifyNoMoreInteractions(itemRepository);
        verifyNoInteractions(itemJPARepository);
        assertFalse(itemList.isEmpty());
    }

    @Test
    public void dadoItemSemPedidos_deveExcluir() {
        // Given
        when(pedidoItemService.findPedidoItemByItemId(any())).thenReturn(TestUtil.getPedidoItemList(0));
        doNothing().when(itemJPARepository).delete(any());

        // When
        boolean excluido = itemService.delete(TestUtil.getItem(TipoItem.SERVICO, true));

        verify(itemJPARepository).delete(any());
        verify(pedidoItemService).findPedidoItemByItemId(any());
        verifyNoMoreInteractions(itemJPARepository);
        verifyNoMoreInteractions(pedidoItemService);
        assertTrue(excluido);
    }

    @Test
    public void dadoItemComPedido_naoDeveExcluir() {
        // Given
        when(pedidoItemService.findPedidoItemByItemId(any())).thenReturn(TestUtil.getPedidoItemList(2));
//        doNothing().when(itemJPARepository).delete(any());

        // When
        boolean excluido = itemService.delete(TestUtil.getItem(TipoItem.SERVICO, true));

        verify(pedidoItemService).findPedidoItemByItemId(any());
        verifyNoInteractions(itemJPARepository);
        verifyNoMoreInteractions(pedidoItemService);
        assertFalse(excluido);
    }
}
