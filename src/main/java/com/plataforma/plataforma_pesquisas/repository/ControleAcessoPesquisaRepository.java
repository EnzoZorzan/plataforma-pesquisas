/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.repository;

import com.plataforma.plataforma_pesquisas.entity.ControleAcessoPesquisa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author enzo.lima
 */
public interface ControleAcessoPesquisaRepository extends JpaRepository<ControleAcessoPesquisa, Long> {

    Optional<ControleAcessoPesquisa>
            findByFormularioIdAndFuncionarioId(Long formularioId, Long funcionarioId);

    List<ControleAcessoPesquisa> findByFormularioIdAndFuncionarioCodigoFunc(Long formularioId, String codigoFunc);

    boolean existsByFormularioIdAndFuncionarioIdAndRespondidoTrue(
            Long formularioId,
            Long funcionarioId
    );
}
