/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.config;

import com.plataforma.plataforma_pesquisas.entity.Empresas;
import com.plataforma.plataforma_pesquisas.entity.Perfil;
import com.plataforma.plataforma_pesquisas.entity.Usuario;
import com.plataforma.plataforma_pesquisas.repository.EmpresasRepository;
import com.plataforma.plataforma_pesquisas.repository.PerfilRepository;
import com.plataforma.plataforma_pesquisas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author enzo.lima
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final EmpresasRepository empresasRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        // =========================
        // PERFIL ADMIN
        // =========================
        Perfil perfil = perfilRepository.findByNome("Administrador")
                .orElseGet(() -> {
                    Perfil p = new Perfil();
                    p.setNome("Administrador");
                    return perfilRepository.save(p);
                });

        // =========================
        // EMPRESA PADRÃO
        // =========================
        Empresas empresa = empresasRepository.findByNome("Empresa Padrão")
                .orElseGet(() -> {
                    Empresas e = new Empresas();
                    e.setNome("Empresa Padrão");
                    return empresasRepository.save(e);
                });

        // =========================
        // USUÁRIO ADMIN
        // =========================
        usuarioRepository.findByEmail("enzozorzanlima@gmail.com")
                .orElseGet(() -> {
                    Usuario admin = new Usuario();
                    admin.setNome("Enzo Zorzan Lima");
                    admin.setEmail("enzozorzanlima@gmail.com");
                    admin.setSenha(passwordEncoder.encode("123456"));
                    admin.setPerfil(perfil);
                    admin.setEmpresa(empresa);
                    return usuarioRepository.save(admin);
                });

        System.out.println("✔ Bootstrap completo");
    }
}
