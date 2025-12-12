/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.entity.ConvitePesquisa;
import com.plataforma.plataforma_pesquisas.repository.ConvitePesquisaRepository;
import com.plataforma.plataforma_pesquisas.service.ConvitePesquisaService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author enzo.lima
 */
@RestController
@RequestMapping("/api/v1/convites")
@RequiredArgsConstructor
public class ConvitesController {
    
    private final ConvitePesquisaRepository conviteRepo;
    private final ConvitePesquisaService conviteService;
    
    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
    public ResponseEntity<List<ConvitePesquisa>> getAllEmpresass() {
        return ResponseEntity.ok(conviteService.findAll());
    }

    @PostMapping("/email/{formularioId}")
    public ResponseEntity<ConvitePesquisa> criarConviteEmail(
            @PathVariable Long formularioId,
            @RequestParam String email
    ) {
        return ResponseEntity.ok(conviteService.gerarConviteEmail(formularioId, email));
    }

    @PostMapping("/totem/{formularioId}")
    public ResponseEntity<Map<String, String>> criarConviteTotem(
            @PathVariable Long formularioId
    ) {
        ConvitePesquisa convite = conviteService.gerarConviteTotem(formularioId);
        return ResponseEntity.ok(Map.of("token", convite.getTokenAcesso()));
    }

    @GetMapping("/validar/{token}")
    public ResponseEntity<?> validarToken(@PathVariable String token) {

        Optional<ConvitePesquisa> conv = conviteService.validarToken(token);

        if (conv.isEmpty()) {
            return ResponseEntity.status(410).body("Token expirado ou inválido");
        }

        return ResponseEntity.ok(conv.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        conviteRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
