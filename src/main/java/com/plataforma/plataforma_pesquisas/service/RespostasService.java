package com.plataforma.plataforma_pesquisas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.entity.Respostas;
import com.plataforma.plataforma_pesquisas.repository.RespostasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RespostasService {

    private final RespostasRepository respostasRepository;
    private final ObjectMapper objectMapper; // Spring Boot já fornece

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

    public List<Respostas> findByFormularioId(Long formularioId) {
        return respostasRepository.findByFormularioId(formularioId);
    }

    public List<Respostas> findByUsuarioId(Long usuarioId) {
        return respostasRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Salva resposta ANÔNIMA (sem usuário) para um formulário, recebendo as
     * respostas como um Map (questao->valor).
     */

    public Respostas salvarRespostaAnonima(Formularios formulario, Map<String, Object> respostasMap) {

        Respostas r = Respostas.builder()
                .formulario(formulario)
                .usuario(null)
                .resposta(respostasMap) // Map direto
                .build();

        return respostasRepository.save(r);
    }

}
