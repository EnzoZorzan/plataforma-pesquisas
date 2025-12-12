/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "convites_pesquisa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvitePesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pode ser nulo em TOTEM
     */
    @Column(nullable = true)
    private String email;

    /**
     * Token único para acesso à pesquisa
     */
    @Column(unique = true, nullable = false)
    private String tokenAcesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formulario_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Formularios formulario;

    private boolean respondido = false;

    private LocalDateTime enviadoEm;
    private LocalDateTime respondidoEm;
}

