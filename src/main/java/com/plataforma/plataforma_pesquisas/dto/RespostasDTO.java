package com.plataforma.plataforma_pesquisas.dto;

import java.util.Map;
import lombok.Data;

@Data
public class RespostasDTO {

    private Long formularioId;
    private Long usuarioId; // opcional (pode ser null para anonimato)
    private Map<String, Object> respostas;
}
