package com.plataforma.plataforma_pesquisas.controller;

import com.plataforma.plataforma_pesquisas.dto.RespostasDTO;
import com.plataforma.plataforma_pesquisas.entity.Formularios;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.plataforma.plataforma_pesquisas.entity.Respostas;
import com.plataforma.plataforma_pesquisas.entity.Usuario;
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

    /**
     * Envio autenticado (interno) das respostas. Pode ter usuário ou ser
     * anônimo.
     */
    @PostMapping
    public ResponseEntity<Respostas> submitRespostas(@RequestBody RespostasDTO req) {

        if (req.getFormularioId() == null || req.getRespostas() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Criar referências sem carregar do banco
        Formularios form = new Formularios();
        form.setId(req.getFormularioId());

        Usuario usuario = null;
        if (req.getUsuarioId() != null) {
            usuario = new Usuario();
            usuario.setId(req.getUsuarioId());
        }

        Respostas r = Respostas.builder()
                .formulario(form)
                .usuario(usuario) // pode ser null (anônimo)
                .resposta(req.getRespostas()) // AGORA JSON REAL!
                .build();

        Respostas saved = respostasService.save(r);
        return ResponseEntity.ok(saved);
    }

    /**
     * Obter todas respostas de um formulário
     */
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
            String usuarioId = r.getUsuario() != null ? String.valueOf(r.getUsuario().getId()) : "";
            String respostaJson = r.getResposta() != null ? r.getResposta().toString().replace("\"", "\"\"") : "";

            sb.append(id).append(",").append(date).append(",").append(usuarioId)
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
}
