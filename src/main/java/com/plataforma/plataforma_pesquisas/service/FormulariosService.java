package com.plataforma.plataforma_pesquisas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.entity.Questoes;
import com.plataforma.plataforma_pesquisas.repository.FormulariosRepository;

import java.util.List;
import java.util.Optional;

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

    public Formularios save(Formularios form) {

        if (form.getId() != null) {
            Formularios existente = formularioRepository.findById(form.getId())
                    .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

            existente.getQuestoes().clear();
            formularioRepository.flush();

            existente.setTitulo(form.getTitulo());
            existente.setDescricao(form.getDescricao());
            existente.setEmpresa(form.getEmpresa());

            for (Questoes q : form.getQuestoes()) {
                q.setFormulario(existente);
                existente.getQuestoes().add(q);
            }

            return formularioRepository.save(existente);
        }

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
