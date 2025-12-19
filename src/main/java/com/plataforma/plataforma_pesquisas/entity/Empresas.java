package com.plataforma.plataforma_pesquisas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "empresas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Empresas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String emailEmp;

    @Column(nullable = false)
    private String telefoneEmp;

    @Column(nullable = false)
    private String enderecoEmp;

    @Column(nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "empresa")
    @JsonIgnore
    private List<Formularios> formularios;
    
    @OneToMany(mappedBy = "empresaFunc", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ListaFuncionariosPesquisa> funcionarios;

}
