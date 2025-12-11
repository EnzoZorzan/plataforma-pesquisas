package com.plataforma.plataforma_pesquisas;

import org.springframework.boot.SpringApplication;

public class TestPlataformaPesquisasApplication {

	public static void main(String[] args) {
		SpringApplication.from(PlataformaPesquisasApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
