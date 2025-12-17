/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.dto;

import com.plataforma.plataforma_pesquisas.entity.Perfil;
import java.util.List;

/**
 *
 * @author enzo.lima
 */
public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Perfil perfil,
        List<String> permissoes
        ) {

}
