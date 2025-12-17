/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.dto.PerfilRequestDTO;
import com.plataforma.plataforma_pesquisas.dto.PerfilResponseDTO;
import com.plataforma.plataforma_pesquisas.entity.Perfil;
import com.plataforma.plataforma_pesquisas.entity.Permissoes;
import com.plataforma.plataforma_pesquisas.repository.PerfilRepository;
import com.plataforma.plataforma_pesquisas.repository.PermissoesRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author enzo.lima
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final PermissoesRepository permissaoRepository;

    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> findById(Long id) {
        return perfilRepository.findById(id);
    }

    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }

    public PerfilResponseDTO create(PerfilRequestDTO dto) {

        Perfil perfil = new Perfil();
        perfil.setNome(dto.nome());

        dto.permissoesIds().forEach(id -> {
            Permissoes p = permissaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Permissão inválida"));
            perfil.getPermissoes().add(p);
        });

        perfilRepository.save(perfil);
        return toDTO(perfil);
    }

    public PerfilResponseDTO update(Long id, PerfilRequestDTO dto) {

        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        perfil.setNome(dto.nome());
        perfil.getPermissoes().clear();

        dto.permissoesIds().forEach(pid -> {
            Permissoes p = permissaoRepository.findById(pid)
                    .orElseThrow();
            perfil.getPermissoes().add(p);
        });

        return toDTO(perfil);
    }

    private PerfilResponseDTO toDTO(Perfil perfil) {
        return new PerfilResponseDTO(
                perfil.getId(),
                perfil.getNome(),
                perfil.getPermissoes()
                        .stream()
                        .map(Permissoes::getCodigo)
                        .toList()
        );
    }

}
