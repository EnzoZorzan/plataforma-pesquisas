package com.plataforma.plataforma_pesquisas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_pesquisas.entity.Usuario;
import com.plataforma.plataforma_pesquisas.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario u) {

        // EDITAR: já existe usuário
        if (u.getId() != null) {
            Usuario existente = usuarioRepository.findById(u.getId())
                    .orElseThrow();

            // Se NÃO enviaram senha → mantém a existente
            if (u.getSenha() == null || u.getSenha().isBlank()) {
                u.setSenha(existente.getSenha());
            } else {
                // Se senha enviada é nova → gerar hash
                u.setSenha(passwordEncoder.encode(u.getSenha()));
            }
            
            if(u.getPerfil() == null){
                u.setPerfil(existente.getPerfil());
            } else {
                u.setPerfil(u.getPerfil());
            }
            
            if(u.getEmpresa() == null){
                u.setEmpresa(existente.getEmpresa());
            } else {
                u.setEmpresa(u.getEmpresa());
            }
            
        } // CRIAR: usuário novo
        else {
            // Sempre hashear senha ao criar
            if (u.getSenha() != null && !u.getSenha().isBlank()) {
                u.setSenha(passwordEncoder.encode(u.getSenha()));
            }
        }

        return usuarioRepository.save(u);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
