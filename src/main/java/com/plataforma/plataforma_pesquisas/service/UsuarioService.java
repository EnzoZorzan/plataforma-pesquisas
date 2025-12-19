package com.plataforma.plataforma_pesquisas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_pesquisas.entity.Usuario;
import com.plataforma.plataforma_pesquisas.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        if (u.getId() != null) {
            Usuario existente = usuarioRepository.findById(u.getId())
                    .orElseThrow();

            // 🔐 SENHA
            if (u.getSenha() == null || u.getSenha().isBlank()) {
                u.setSenha(existente.getSenha());
            } else {
                // 🔒 Só criptografa se NÃO for hash
                if (!u.getSenha().startsWith("$2")) {
                    u.setSenha(passwordEncoder.encode(u.getSenha()));
                }
            }

            // PERFIL
            if (u.getPerfil() == null) {
                u.setPerfil(existente.getPerfil());
            }

            // EMPRESA
            if (u.getEmpresa() == null) {
                u.setEmpresa(existente.getEmpresa());
            }

        } else {
            // CRIAR usuário
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

    public UserDetails loadUserByUsername(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<SimpleGrantedAuthority> authorities
                = usuario.getPerfil().getPermissoes().stream()
                        .map(p -> new SimpleGrantedAuthority(p.getCodigo()))
                        .toList();

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                authorities
        );
    }

}
