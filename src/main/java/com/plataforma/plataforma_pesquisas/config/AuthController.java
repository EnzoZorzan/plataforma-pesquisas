/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.config;

/**
 *
 * @author enzo.lima
 */
import com.plataforma.plataforma_pesquisas.dto.LoginRequest;
import com.plataforma.plataforma_pesquisas.dto.LoginResponse;
import com.plataforma.plataforma_pesquisas.entity.Permissoes;
import com.plataforma.plataforma_pesquisas.entity.ResetToken;
import com.plataforma.plataforma_pesquisas.entity.Usuario;
import com.plataforma.plataforma_pesquisas.repository.ResetTokenRepository;
import com.plataforma.plataforma_pesquisas.service.UsuarioService;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ResetTokenRepository resetTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        Usuario usuario = usuarioService.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email inválido"));

        if (!passwordEncoder.matches(req.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        List<String> permissoes = usuario.getPerfil()
                .getPermissoes()
                .stream()
                .map(Permissoes::getCodigo)
                .toList();

        String token = jwtUtil.generateToken(usuario, permissoes);

        return ResponseEntity.ok(new LoginResponse(token, usuario));
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> recuperar(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));

        String token = UUID.randomUUID().toString();

        ResetToken rt = ResetToken.builder()
                .token(token)
                .usuario(usuario)
                .expiracao(LocalDateTime.now().plusHours(2))
                .build();

        resetTokenRepository.save(rt);

        System.out.println("==== LINK DE RECUPERAÇÃO ====");
        System.out.println("http://localhost:5173/redefinir-senha/" + token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinir(@RequestBody Map<String, String> body) {

        String token = body.get("token");
        String novaSenha = body.get("senha");

        ResetToken rt = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (rt.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        Usuario u = rt.getUsuario();
        u.setSenha(passwordEncoder.encode(novaSenha)); // ✔ correto
        usuarioService.save(u);

        resetTokenRepository.delete(rt);

        return ResponseEntity.ok().build();
    }
}
