package com.higuti.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	private static final Logger log = LoggerFactory.getLogger(BankApplication.class);

	public static void main(String[] args) {
		log.info("Iniciando a aplicação bancária...");

		SpringApplication.run(BankApplication.class, args);

		log.info("Aplicação bancária inicializada e pronta para receber requisições");
	}
}