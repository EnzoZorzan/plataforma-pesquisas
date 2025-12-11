package com.plataforma.plataforma_pesquisas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.plataforma.plataforma_pesquisas.entity.Respostas;
import com.plataforma.plataforma_pesquisas.service.RespostasService;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/respostas")
@RequiredArgsConstructor
public class RespostasController {

    private final RespostasService responseService;

//    @GetMapping("/survey/{formularioId}")
////    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<Respostas>> getRespostassBySurvey(@PathVariable Long formularioId) {
//        return ResponseEntity.ok(responseService.findBySurveyId(formularioId));
//    }

    @PostMapping
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Respostas> submitRespostas(@RequestBody Respostas resposta) {
        return ResponseEntity.ok(responseService.save(resposta));
    }
}
