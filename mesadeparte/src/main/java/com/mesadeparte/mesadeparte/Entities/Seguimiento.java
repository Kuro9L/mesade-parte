package com.mesadeparte.mesadeparte.Entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idseguimiento;

    @Column(nullable = false)
    private Date fecha;

    @Column(length = 200, nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String archivo;

    // Relación muchos a uno con Documento
    @ManyToOne
    @JoinColumn(name = "documentoid", nullable = false)
    private Documento documento;

    // Relación muchos a uno con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario", nullable = false)
    private Usuario usuario;
}
