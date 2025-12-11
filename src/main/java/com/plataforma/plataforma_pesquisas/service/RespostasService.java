package com.plataforma.plataforma_pesquisas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_pesquisas.entity.Respostas;
import com.plataforma.plataforma_pesquisas.repository.RespostasRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RespostasService {

    private final RespostasRepository respostasRepository;

    public List<Respostas> findAll() {
        return respostasRepository.findAll();
    }

    public Optional<Respostas> findById(Long id) {
        return respostasRepository.findById(id);
    }

    public Respostas save(Respostas resposta) {
        return respostasRepository.save(resposta);
    }

    public void delete(Long id) {
        respostasRepository.deleteById(id);
    }

}
