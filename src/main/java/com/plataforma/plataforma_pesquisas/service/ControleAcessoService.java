/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.entity.ControleAcessoPesquisa;
import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.entity.ListaFuncionariosPesquisa;
import com.plataforma.plataforma_pesquisas.repository.ControleAcessoPesquisaRepository;
import com.plataforma.plataforma_pesquisas.repository.ListaFuncionariosPesquisaRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author enzo.lima
 */
@Service
@RequiredArgsConstructor
public class ControleAcessoService {

    private final ListaFuncionariosPesquisaRepository funcionarioRepo;
    private final ControleAcessoPesquisaRepository acessoRepo;

    public ListaFuncionariosPesquisa validarCodigo(
            Long formularioId,
            String codigoFunc
    ) {
        ListaFuncionariosPesquisa funcionario
                = funcionarioRepo.findByCodigoFunc(codigoFunc)
                        .orElseThrow(() -> new RuntimeException("Código inválido"));

        boolean jaRespondeu
                = acessoRepo.existsByFormularioIdAndFuncionarioIdAndRespondidoTrue(
                        formularioId,
                        funcionario.getId()
                );

        if (jaRespondeu) {
            throw new RuntimeException("Pesquisa já respondida");
        }

        return funcionario;
    }

    public void registrarResposta(
            Formularios formulario,
            ListaFuncionariosPesquisa funcionario
    ) {
        ControleAcessoPesquisa acesso = ControleAcessoPesquisa.builder()
                .formulario(formulario)
                .funcionario(funcionario)
                .respondido(true)
                .respondidoEm(LocalDateTime.now())
                .build();

        acessoRepo.save(acesso);
    }

}
