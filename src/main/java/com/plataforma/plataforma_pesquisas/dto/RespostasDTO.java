/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.plataforma_pesquisas.dto;

import java.util.Map;
import lombok.Data;

/**
 *
 * @author enzo.lima
 */
@Data
public class RespostasDTO {

    private Long formularioId;
    private Map<String, Object> respostas;
    private String codigoFunc;

}
