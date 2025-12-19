/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.dto.ValidarAcessoRequest;
import com.plataforma.plataforma_pesquisas.service.ControleAcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author enzo.lima
 */
@RestController
@RequestMapping("/api/v1/acesso")
@RequiredArgsConstructor
public class ControleAcessoController {

    private final ControleAcessoService acessoService;

    @PostMapping("/validar")
    public ResponseEntity<?> validarCodigo(@RequestBody ValidarAcessoRequest req) {

        if (req.getFormularioId() == null || req.getCodigoFunc() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            acessoService.validarCodigo(
                    req.getFormularioId(),
                    req.getCodigoFunc()
            );
            return ResponseEntity.ok().build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

}
