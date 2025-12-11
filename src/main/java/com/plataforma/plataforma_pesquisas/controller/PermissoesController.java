/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.entity.Permissoes;
import com.plataforma.plataforma_pesquisas.service.PermissoesService;
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
@RequestMapping("/api/v1/permissoes")
@RequiredArgsConstructor
public class PermissoesController {

    private final PermissoesService permissoesService;

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Permissoes>> getAllPermissoes() {
        return ResponseEntity.ok(permissoesService.findAll());
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permissoes> createPermissoes(@RequestBody Permissoes permissao) {
        return ResponseEntity.ok(permissoesService.save(permissao));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permissoes> updatePermissoes(@PathVariable Long id, @RequestBody Permissoes permissao) {
        permissao.setId(id);
        return ResponseEntity.ok(permissoesService.save(permissao));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePermissoes(@PathVariable Long id) {
        permissoesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
