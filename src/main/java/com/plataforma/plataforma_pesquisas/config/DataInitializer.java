/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.config;

import com.plataforma.plataforma_pesquisas.entity.Usuario;
import com.plataforma.plataforma_pesquisas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author enzo.lima
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {

        if (usuarioRepository.count() > 0) return;

        Usuario admin = new Usuario();
        admin.setNome("Enzo Zorzan Lima");
        admin.setEmail("enzozorzanlima@gmail.com");
        admin.setSenha(passwordEncoder.encode("123456"));

        usuarioRepository.save(admin);

        System.out.println("✔ Usuário admin criado");
    }
}

