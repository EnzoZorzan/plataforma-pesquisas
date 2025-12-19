/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.entity.ListaFuncionariosPesquisa;
import com.plataforma.plataforma_pesquisas.service.ListaFuncionariosPesquisaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author enzo.lima
 */
@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
public class ListaFuncionariosPesquisaController {

    private final ListaFuncionariosPesquisaService funcionariosService;

    @GetMapping
    @PreAuthorize("hasAuthority('FUNCIONARIOS_CADASTRO')")
    public ResponseEntity<List<ListaFuncionariosPesquisa>> getAllListaFuncionariosPesquisas() {
        return ResponseEntity.ok(funcionariosService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('FUNCIONARIOS_CADASTRO')")
    public ResponseEntity<ListaFuncionariosPesquisa> getListaFuncionariosPesquisa(@PathVariable Long id) {
        return funcionariosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FUNCIONARIOS_CADASTRO')")
    public ResponseEntity<ListaFuncionariosPesquisa> createListaFuncionariosPesquisa(@RequestBody ListaFuncionariosPesquisa funcionario) {
        return ResponseEntity.ok(funcionariosService.save(funcionario));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('FUNCIONARIOS_CADASTRO')")
    public ResponseEntity<ListaFuncionariosPesquisa> updateListaFuncionariosPesquisa(@PathVariable Long id, @RequestBody ListaFuncionariosPesquisa funcionario) {
        funcionario.setId(id);
        return ResponseEntity.ok(funcionariosService.save(funcionario));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('FUNCIONARIOS_CADASTRO')")
    public ResponseEntity<Void> deleteListaFuncionariosPesquisa(@PathVariable Long id) {
        funcionariosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/importar-csv")
    @PreAuthorize("hasAuthority('FUNCIONARIOS_CADASTRO')")
    public ResponseEntity<?> importarCsv(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo CSV não enviado");
        }

        funcionariosService.importarCsv(file);
        return ResponseEntity.ok().build();
    }

}
