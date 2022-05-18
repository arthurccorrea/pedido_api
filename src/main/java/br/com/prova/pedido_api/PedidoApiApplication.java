package br.com.prova.pedido_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.PersistenceContext;

@SpringBootApplication
@PersistenceContext
public class PedidoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidoApiApplication.class, args);
	}

}
