/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.service;

import com.plataforma.plataforma_pesquisas.entity.Empresas;
import com.plataforma.plataforma_pesquisas.entity.ListaFuncionariosPesquisa;
import com.plataforma.plataforma_pesquisas.repository.EmpresasRepository;
import com.plataforma.plataforma_pesquisas.repository.ListaFuncionariosPesquisaRepository;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author enzo.lima
 */
@Service
@RequiredArgsConstructor
public class ListaFuncionariosPesquisaService {

    private final ListaFuncionariosPesquisaRepository funcionariosRepository;
    private final EmpresasRepository empresasRepository;

    public List<ListaFuncionariosPesquisa> findAll() {
        return funcionariosRepository.findAll();
    }

    public ListaFuncionariosPesquisa save(ListaFuncionariosPesquisa f) {

        // EDIÇÃO
        if (f.getId() != null) {
            ListaFuncionariosPesquisa existente = funcionariosRepository.findById(f.getId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

            // mantém empresa se não vier do front
            if (f.getEmpresaFunc() == null) {
                f.setEmpresaFunc(existente.getEmpresaFunc());
            }
        }

        // NOVO ou EDITADO
        return funcionariosRepository.save(f);
    }

    public Optional<ListaFuncionariosPesquisa> findById(Long id) {
        return funcionariosRepository.findById(id);
    }

    public void delete(Long id) {
        funcionariosRepository.deleteById(id);
    }

    @Transactional
    public void importarCsv(MultipartFile file) {

        int salvos = 0;
        int ignorados = 0;

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
                )) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {

                if (header) {
                    header = false;
                    continue;
                }

                // detecta delimitador automaticamente
                String delimiter = line.contains(";") ? ";" : ",";
                String[] cols = line.split(delimiter);

                if (cols.length < 3) {
                    ignorados++;
                    continue;
                }

                try {
                    String nome = cols[0].trim();
                    String codigo = cols[1].trim();
                    String empresaValor = cols[2].trim();

                    Empresas empresa;
                    if (empresaValor.matches("\\d+")) {
                        Long empresaId = Long.parseLong(empresaValor);
                        empresa = empresasRepository.findById(empresaId)
                                .orElseThrow(()
                                        -> new RuntimeException("Empresa não encontrada: " + empresaId));
                    } else {
                        empresa = empresasRepository.findByNomeIgnoreCase(empresaValor)
                                .orElseThrow(()
                                        -> new RuntimeException("Empresa não encontrada: " + empresaValor));
                    }

                    boolean existe
                            = funcionariosRepository.existsByCodigoFuncAndEmpresaFuncId(
                                    codigo, empresa.getId()
                            );

                    if (existe) {
                        ignorados++;
                        continue;
                    }

                    ListaFuncionariosPesquisa func = ListaFuncionariosPesquisa.builder()
                            .nomeFunc(nome)
                            .codigoFunc(codigo)
                            .empresaFunc(empresa)
                            .build();

                    funcionariosRepository.save(func);
                    salvos++;

                } catch (Exception e) {
                    ignorados++;
                    // loga e continua
                    System.err.println("Linha ignorada: " + line);
                    System.err.println(e.getMessage());
                }
            }

            System.out.println("Importação finalizada. Salvos: " + salvos + ", Ignorados: " + ignorados);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar CSV", e);
        }
    }

}
