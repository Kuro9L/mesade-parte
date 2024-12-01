package com.mesadeparte.mesadeparte.Services;

import java.util.List;

import com.mesadeparte.mesadeparte.Entities.Seguimiento;

public interface SeguimientoServices {

    List<Seguimiento> listSeguimiento();

    Seguimiento saveSeguimiento(Seguimiento seguimiento);

    Seguimiento leeIdSeguimiento(Long id);

    void deleteSeguimiento(Long id);
}
