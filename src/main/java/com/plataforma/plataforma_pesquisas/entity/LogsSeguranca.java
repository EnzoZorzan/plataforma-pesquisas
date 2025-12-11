package com.plataforma.plataforma_pesquisas.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "logs_seguranca")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogsSeguranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entidade;

    private String acao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(columnDefinition = "jsonb")
    private String detalhes;

    @CreationTimestamp
    private LocalDateTime dataRegistro;
}
