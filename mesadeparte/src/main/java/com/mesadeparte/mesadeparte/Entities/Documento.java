package com.mesadeparte.mesadeparte.Entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numexpediente;

    @Column(length = 1, nullable = false)
    private String tipodoc;

    @Column(length = 1, nullable = false)
    private String folio;

    @Column(length = 200, nullable = false)
    private String asunto;

    @Column(nullable = true)
    private String archivo;

    @Column(nullable = true)
    private Date fecha;

    @Column(length = 4, nullable = true)
    private String codseguridad;

    @Column(length = 1, nullable = true)
    private String estado;


    //Documentos de Remitente//
    @Column(length = 1, nullable = false)
    private String tipopersona;

    @Column(length = 20, nullable = false)
    private String numdoc;

    @Column(length = 100, nullable = false)
    private String nombres;

    @Column(length = 100, nullable = true)
    private String correo;

    @Column(length = 9, nullable = true)
    private String telefono;

    // Relación uno a muchos con Seguimiento
    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> seguimientos;
}
