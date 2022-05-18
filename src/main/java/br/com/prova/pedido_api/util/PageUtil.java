package br.com.prova.pedido_api.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class PageUtil {

    private static final int rowsPerPage = 20;

    public static Pageable getPageable(int page) {
        return PageRequest.of(page, rowsPerPage);
    }

}
