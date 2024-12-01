package com.mesadeparte.mesadeparte.Services;

import java.util.List;

import com.mesadeparte.mesadeparte.Entities.Documento;

public interface DocumentoServices {
    List<Documento> listDocumento();

    Documento saveDocumento(Documento documento);

    Documento leeIdDocumento(Long id);

    void deleteDocumento(Long id);
}
