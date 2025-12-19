/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author enzo.lima
 */
@Entity
@Table(name = "controle_acesso_pesquisa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ControleAcessoPesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formulario_id", nullable = false)
    private Formularios formulario;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private ListaFuncionariosPesquisa funcionario;

    @Column(nullable = false)
    private boolean respondido;

    private LocalDateTime respondidoEm;
}

