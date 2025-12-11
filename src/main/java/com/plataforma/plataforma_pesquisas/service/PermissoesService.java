/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.entity.Permissoes;
import com.plataforma.plataforma_pesquisas.repository.PermissoesRepository;
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
public class PermissoesService {

    private final PermissoesRepository permissoesRepository;

    public List<Permissoes> findAll() {
        return permissoesRepository.findAll();
    }

    public Optional<Permissoes> findById(Long id) {
        return permissoesRepository.findById(id);
    }

    public Permissoes save(Permissoes permissao) {
        return permissoesRepository.save(permissao);
    }

    public void delete(Long id) {
        permissoesRepository.deleteById(id);
    }
    
}
