/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.repository;

import com.plataforma.plataforma_pesquisas.entity.ListaFuncionariosPesquisa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author enzo.lima
 */
public interface ListaFuncionariosPesquisaRepository extends JpaRepository<ListaFuncionariosPesquisa, Long> {

    Optional<ListaFuncionariosPesquisa> findByCodigoFunc(String codigoFunc);
    
    boolean existsByCodigoFuncAndEmpresaFuncId(String codigoFunc, Long empresaId);

}
