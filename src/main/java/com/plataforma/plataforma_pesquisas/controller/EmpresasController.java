/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.entity.Empresas;
import com.plataforma.plataforma_pesquisas.service.EmpresasService;
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
@RequestMapping("/api/v1/empresas")
@RequiredArgsConstructor
public class EmpresasController {
    
    private final EmpresasService empresasService;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
    public ResponseEntity<List<Empresas>> getAllEmpresass() {
        return ResponseEntity.ok(empresasService.findAll());
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
    public ResponseEntity<Empresas> getEmpresas(@PathVariable Long id) {
        return empresasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
    public ResponseEntity<Empresas> createEmpresas(@RequestBody Empresas empresas) {
        return ResponseEntity.ok(empresasService.save(empresas));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
    public ResponseEntity<Empresas> updateEmpresas(@PathVariable Long id, @RequestBody Empresas empresas) {
        empresas.setId(id);
        return ResponseEntity.ok(empresasService.save(empresas));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
    public ResponseEntity<Void> deleteEmpresas(@PathVariable Long id) {
        empresasService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
