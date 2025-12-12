package com.plataforma.plataforma_pesquisas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.service.FormulariosService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/formularios")
@RequiredArgsConstructor
public class FormulariosController {

    private final FormulariosService formulariosService;

    @GetMapping
    public ResponseEntity<List<Formularios>> getAll() {
        return ResponseEntity.ok(formulariosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formularios> get(@PathVariable Long id) {
        return formulariosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Formularios> create(@RequestBody Formularios formulario) {
        return ResponseEntity.ok(formulariosService.save(formulario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Formularios> update(@PathVariable Long id,
            @RequestBody Formularios formulario) {
        formulario.setId(id);
        return ResponseEntity.ok(formulariosService.save(formulario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formulariosService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
