package com.pinheiro.ebook.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ebooks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String autor;
    @Column
    private String categoria;
    @Column
    private String urlCapa;
    @Column
    private String texto;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @UpdateTimestamp
    private LocalDateTime ultimaAtualizacao;
}
