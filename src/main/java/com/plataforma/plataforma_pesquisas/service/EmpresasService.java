/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.entity.Empresas;
import com.plataforma.plataforma_pesquisas.repository.EmpresasRepository;
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
public class EmpresasService {

    private final EmpresasRepository empresaRepository;

    public List<Empresas> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresas> findById(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresas save(Empresas empresa) {
        return empresaRepository.save(empresa);
    }

    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }

}
