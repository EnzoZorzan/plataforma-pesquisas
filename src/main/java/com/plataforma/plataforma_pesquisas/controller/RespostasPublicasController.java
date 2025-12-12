/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.dto.RespostaPublicaRequest;
import com.plataforma.plataforma_pesquisas.entity.ConvitePesquisa;
import com.plataforma.plataforma_pesquisas.service.ConvitePesquisaService;
import com.plataforma.plataforma_pesquisas.service.RespostasService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/respostas-publicas")
@RequiredArgsConstructor
public class RespostasPublicasController {

    private final ConvitePesquisaService conviteService;
    private final RespostasService respostasService;

    @PostMapping
    public ResponseEntity<?> enviarResposta(@RequestBody RespostaPublicaRequest dto) {
        ConvitePesquisa convite = conviteService.validarToken(dto.getToken())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.GONE, "Token inválido ou já respondido"));

        // salva resposta anônima
        respostasService.salvarRespostaAnonima(convite.getFormulario(), dto.getRespostas());

        // marca token usado
        conviteService.marcarComoRespondido(convite);

        return ResponseEntity.ok().body(Map.of("status", "ok"));
    }
}
