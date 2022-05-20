package br.com.prova.pedido_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.PersistenceContext;

@SpringBootApplication
@PersistenceContext
public class PedidoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidoApiApplication.class, args);
	}

}
