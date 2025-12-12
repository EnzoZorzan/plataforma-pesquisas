package com.plataforma.plataforma_pesquisas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataforma.plataforma_pesquisas.entity.Respostas;
import java.util.List;

public interface RespostasRepository extends JpaRepository<Respostas, Long> {

    List<Respostas> findByFormularioId(Long formularioId);

    List<Respostas> findByUsuarioId(Long usuarioId);
}
