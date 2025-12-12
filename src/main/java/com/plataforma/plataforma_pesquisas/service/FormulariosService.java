package com.plataforma.plataforma_pesquisas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.entity.Questoes;
import com.plataforma.plataforma_pesquisas.repository.FormulariosRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormulariosService {

    private final FormulariosRepository formularioRepository;

    public List<Formularios> findAll() {
        return formularioRepository.findAll();
    }

    public Optional<Formularios> findById(Long id) {
        return formularioRepository.findById(id);
    }

    @Transactional
    public Formularios save(Formularios form) {

        if (form.getId() != null) {

            Formularios existente = formularioRepository.findById(form.getId())
                    .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

            existente.setTitulo(form.getTitulo());
            existente.setDescricao(form.getDescricao());
            existente.setEmpresa(form.getEmpresa());

            // mapa de questões já existentes
            Map<Long, Questoes> mapaExistentes = existente.getQuestoes()
                    .stream()
                    .collect(Collectors.toMap(Questoes::getId, q -> q));

            // 1️⃣ Remover questões que não vieram no payload
            List<Long> idsEnviados = form.getQuestoes()
                    .stream()
                    .map(Questoes::getId)
                    .filter(Objects::nonNull)
                    .toList();

            existente.getQuestoes().removeIf(q -> q.getId() != null && !idsEnviados.contains(q.getId()));

            // 2️⃣ Atualizar + adicionar novas
            for (Questoes q : form.getQuestoes()) {

                if (q.getId() == null) {
                    // nova questão
                    q.setFormulario(existente);
                    existente.getQuestoes().add(q);
                    continue;
                }

                // questão existente
                Questoes original = mapaExistentes.get(q.getId());
                if (original != null) {
                    original.setDescricaoPergunta(q.getDescricaoPergunta());
                    original.setQtype(q.getQtype());
                    original.setOptions(q.getOptions());
                    original.setOrd(q.getOrd());
                }
            }

            return existente;
        }

        // criar novo
        Formularios novo = formularioRepository.save(form);
        for (Questoes q : form.getQuestoes()) {
            q.setFormulario(novo);
        }

        return formularioRepository.save(novo);
    }

    public void delete(Long id) {
        formularioRepository.deleteById(id);
    }
}
