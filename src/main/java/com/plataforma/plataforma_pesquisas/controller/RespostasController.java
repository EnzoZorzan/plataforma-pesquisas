package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.dto.RespostasDTO;
import com.plataforma.plataforma_pesquisas.entity.Formularios;
import com.plataforma.plataforma_pesquisas.entity.ListaFuncionariosPesquisa;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.plataforma.plataforma_pesquisas.entity.Respostas;
import com.plataforma.plataforma_pesquisas.service.ControleAcessoService;
import com.plataforma.plataforma_pesquisas.service.RespostasService;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/respostas")
@RequiredArgsConstructor
public class RespostasController {

    private final RespostasService respostasService;
    private final ControleAcessoService acessoService;

    @GetMapping("/formulario/{id}")
    public ResponseEntity<List<Respostas>> getByFormulario(@PathVariable Long id) {
        return ResponseEntity.ok(respostasService.findByFormularioId(id));
    }

    /**
     * Buscar uma resposta específica
     */
    @GetMapping("/{id}")
    public ResponseEntity<Respostas> getOne(@PathVariable Long id) {
        return respostasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Excluir resposta
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        respostasService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Exportar CSV (json é convertido automaticamente para string)
     */
    @GetMapping("/export/csv/{formularioId}")
    public ResponseEntity<InputStreamResource> exportCsv(@PathVariable Long formularioId) {

        List<Respostas> list = respostasService.findByFormularioId(formularioId);

        StringBuilder sb = new StringBuilder();
        sb.append("id,dataResposta,usuarioId,respostaJson\n");

        for (Respostas r : list) {
            String id = String.valueOf(r.getId());
            String date = r.getDataResposta() != null ? r.getDataResposta().toString() : "";
            String respostaJson = r.getResposta() != null ? r.getResposta().toString().replace("\"", "\"\"") : "";

            sb.append(id).append(",").append(date).append(",")
                    .append(",\"").append(respostaJson).append("\"\n");
        }

        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=respostas_form_" + formularioId + ".csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(bais));
    }

    @PostMapping("/respostas-publicas")
    public ResponseEntity<?> enviarResposta(@RequestBody RespostasDTO dto) {

        ListaFuncionariosPesquisa funcionario
                = acessoService.validarCodigo(
                        dto.getFormularioId(),
                        dto.getCodigoFunc()
                );

        Formularios formulario = new Formularios();
        formulario.setId(dto.getFormularioId());

        respostasService.salvarRespostaAnonima(
                formulario,
                dto.getRespostas()
        );

        acessoService.registrarResposta(formulario, funcionario);

        return ResponseEntity.ok().build();
    }

}
