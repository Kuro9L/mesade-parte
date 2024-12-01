package com.mesadeparte.mesadeparte.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesadeparte.mesadeparte.Entities.Seguimiento;
import com.mesadeparte.mesadeparte.Repository.SeguimientoRepository;

@Service
public class SeguimientoServicesImpl implements SeguimientoServices {

    @Autowired
    SeguimientoRepository repository;

    @Override
    public List<Seguimiento> listSeguimiento() {
        return repository.findAll();
    }

    @Override
    public Seguimiento saveSeguimiento(Seguimiento seguimiento) {
        return repository.save(seguimiento);
    }

    @Override
    public Seguimiento leeIdSeguimiento(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteSeguimiento(Long id) {
        repository.deleteById(id);
    }

}
