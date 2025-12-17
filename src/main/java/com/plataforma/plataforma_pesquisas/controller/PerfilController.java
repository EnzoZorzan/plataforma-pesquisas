/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.dto.PerfilRequestDTO;
import com.plataforma.plataforma_pesquisas.dto.PerfilResponseDTO;
import com.plataforma.plataforma_pesquisas.entity.Perfil;
import com.plataforma.plataforma_pesquisas.service.PerfilService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author enzo.lima
 */
@RestController
@RequestMapping("/api/v1/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilsService;

    @GetMapping
    public ResponseEntity<List<Perfil>> getAllPerfils() {
        return ResponseEntity.ok(perfilsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> getPerfil(@PathVariable Long id) {
        return perfilsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PerfilResponseDTO create(@RequestBody PerfilRequestDTO dto) {
        return perfilsService.create(dto);
    }

    @PutMapping("/{id}")
    public PerfilResponseDTO update(
            @PathVariable Long id,
            @RequestBody PerfilRequestDTO dto) {
        return perfilsService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        perfilsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
