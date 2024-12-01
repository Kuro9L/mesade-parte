package com.mesadeparte.mesadeparte.Entities;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SeguimientoDto {

    private Long idseguimiento;
    private Date fecha;
    private String descripcion;
    private MultipartFile archivo;
    private Documento documento;
    private Usuario usuario;
}
