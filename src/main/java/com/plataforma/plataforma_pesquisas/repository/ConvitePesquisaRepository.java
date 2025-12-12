/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.repository;

import com.plataforma.plataforma_pesquisas.entity.ConvitePesquisa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author enzo.lima
 */
public interface ConvitePesquisaRepository extends JpaRepository<ConvitePesquisa, Long> {

    Optional<ConvitePesquisa> findByTokenAcesso(String token);

    List<ConvitePesquisa> findByFormularioId(Long formularioId);
}

