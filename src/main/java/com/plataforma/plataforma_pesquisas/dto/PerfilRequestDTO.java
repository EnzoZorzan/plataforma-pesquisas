/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.dto;

import java.util.List;

/**
 *
 * @author enzo.lima
 */
public record PerfilRequestDTO(
    String nome,
    List<Long> permissoesIds
) {}
