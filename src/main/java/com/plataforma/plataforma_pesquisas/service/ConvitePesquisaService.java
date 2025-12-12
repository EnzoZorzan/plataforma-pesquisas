/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.entity.ConvitePesquisa;
import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.repository.ConvitePesquisaRepository;
import com.plataforma.plataforma_pesquisas.repository.FormulariosRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author enzo.lima
 */
@Service
@RequiredArgsConstructor
public class ConvitePesquisaService {

    private final ConvitePesquisaRepository conviteRepo;
    private final FormulariosRepository formularioRepo;

    public ConvitePesquisa gerarConviteEmail(Long formularioId, String email) {
        Formularios form = formularioRepo.findById(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

        ConvitePesquisa convite = ConvitePesquisa.builder()
                .email(email)
                .formulario(form)
                .tokenAcesso(UUID.randomUUID().toString())
                .respondido(false)
                .enviadoEm(LocalDateTime.now())
                .build();

        return conviteRepo.save(convite);
    }

    public ConvitePesquisa gerarConviteTotem(Long formularioId) {
        Formularios form = formularioRepo.findById(formularioId)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

        ConvitePesquisa convite = ConvitePesquisa.builder()
                .email(null)
                .formulario(form)
                .tokenAcesso(UUID.randomUUID().toString())
                .respondido(false)
                .enviadoEm(LocalDateTime.now())
                .build();

        return conviteRepo.save(convite);
    }

    public Optional<ConvitePesquisa> validarToken(String token) {
        return conviteRepo.findByTokenAcesso(token)
                .filter(c -> !c.isRespondido());
    }

    public void marcarComoRespondido(ConvitePesquisa convite) {
        convite.setRespondido(true);
        convite.setRespondidoEm(LocalDateTime.now());
        conviteRepo.save(convite);
    }
    
    public List<ConvitePesquisa> findAll() {
        return conviteRepo.findAll();
    }

    public Optional<ConvitePesquisa> findById(Long id) {
        return conviteRepo.findById(id);
    }

    public ConvitePesquisa save(ConvitePesquisa empresa) {
        return conviteRepo.save(empresa);
    }

    public void delete(Long id) {
        conviteRepo.deleteById(id);
    }
}
