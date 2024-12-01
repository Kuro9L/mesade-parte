package com.mesadeparte.mesadeparte.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesadeparte.mesadeparte.Entities.Documento;
import com.mesadeparte.mesadeparte.Repository.DocumentoRepository;

@Service
public class DocumentoServicesImpl implements DocumentoServices {

    @Autowired
    DocumentoRepository repository;

    @Override
    public List<Documento> listDocumento() {
        return repository.findAll();
    }
    @Override
    public Documento saveDocumento(Documento documento) {
        return repository.save(documento);
    }

    @Override
    public Documento leeIdDocumento(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteDocumento(Long id) {
        repository.deleteById(id);
    }

}
