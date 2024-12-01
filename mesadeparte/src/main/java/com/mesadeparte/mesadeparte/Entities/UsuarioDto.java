package com.mesadeparte.mesadeparte.Entities;

import lombok.Data;

@Data
public class UsuarioDto {
    private String dni;
    private String apellidos;
    private String nombres;
    private String clave;
    private int tipo;
    private String estado = "1";
}
