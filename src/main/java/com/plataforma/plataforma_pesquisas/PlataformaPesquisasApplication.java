package com.plataforma.plataforma_pesquisas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.plataforma.plataforma_pesquisas.entity")
@EnableJpaRepositories("com.plataforma.plataforma_pesquisas.repository")
public class PlataformaPesquisasApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlataformaPesquisasApplication.class, args);
    }
}
