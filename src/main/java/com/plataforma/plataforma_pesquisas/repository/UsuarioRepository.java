package com.plataforma.plataforma_pesquisas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataforma.plataforma_pesquisas.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
