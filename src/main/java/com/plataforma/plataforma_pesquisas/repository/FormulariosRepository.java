package com.plataforma.plataforma_pesquisas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataforma.plataforma_pesquisas.entity.Formularios;
import java.util.Optional;


public interface FormulariosRepository extends JpaRepository<Formularios, Long> {
    
    Optional<Formularios> findFirstByAtivoTrueAndEmpresaIsNullOrderByDataCriacaoDesc();
}
