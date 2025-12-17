/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.dto.PermissaoRequestDTO;
import com.plataforma.plataforma_pesquisas.dto.PermissaoResponseDTO;
import com.plataforma.plataforma_pesquisas.entity.Permissoes;
import com.plataforma.plataforma_pesquisas.repository.PermissoesRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author enzo.lima
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PermissoesService {

    private final PermissoesRepository repository;

    public List<PermissaoResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PermissaoResponseDTO create(PermissaoRequestDTO dto) {

        Permissoes permissao = Permissoes.builder()
                .codigo(dto.codigo().toUpperCase())
                .descricao(dto.descricao())
                .build();

        repository.save(permissao);
        return toDTO(permissao);
    }

    public PermissaoResponseDTO update(Long id, PermissaoRequestDTO dto) {

        Permissoes permissao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permissão não encontrada"));

        permissao.setCodigo(dto.codigo().toUpperCase());
        permissao.setDescricao(dto.descricao());

        return toDTO(permissao);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private PermissaoResponseDTO toDTO(Permissoes p) {
        return new PermissaoResponseDTO(
                p.getId(),
                p.getCodigo(),
                p.getDescricao()
        );
    }
}

