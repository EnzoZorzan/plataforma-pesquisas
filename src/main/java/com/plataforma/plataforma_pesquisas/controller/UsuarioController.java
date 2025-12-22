package com.plataforma.plataforma_pesquisas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.plataforma.plataforma_pesquisas.entity.Usuario;
import com.plataforma.plataforma_pesquisas.service.UsuarioService;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuariosService;

    @GetMapping
//    @PreAuthorize("hasAuthority('USUARIOS_CADASTRO')")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuariosService.findAll());
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('USUARIOS_CADASTRO')")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        return usuariosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('USUARIOS_CADASTRO')")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuariosService.save(usuario));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('USUARIOS_CADASTRO')")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return ResponseEntity.ok(usuariosService.save(usuario));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('USUARIOS_CADASTRO')")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuariosService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
