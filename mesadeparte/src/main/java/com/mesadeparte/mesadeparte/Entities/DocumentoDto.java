package com.mesadeparte.mesadeparte.Entities;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class DocumentoDto {
    private Long numexpediente;
    private String tipodoc;
    private String folio;
    private String asunto;
    private MultipartFile archivo;
    private Date fecha;
    private String codseguridad;
    private String estado;

    private String tipopersona;
    private String numdoc;
    private String nombres;
    private String correo;
    private String telefono;

}
