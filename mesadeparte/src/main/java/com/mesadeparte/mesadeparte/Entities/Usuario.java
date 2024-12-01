package com.mesadeparte.mesadeparte.Entities;

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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    @Column(length = 8, nullable = false)
    private String dni;
    @Column(length = 60, nullable = false)
    private String apellidos;
    @Column(length = 60, nullable = false)
    private String nombres;
    @Column(length = 15, nullable = false)
    private String clave;
    @Column(length = 1, nullable = false)
    private int tipo;
    @Column(length = 8, nullable = false)
    private String estado = "1";

    // Relación uno a muchos con Seguimiento
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> seguimientos; // Nombre actualizado
}
