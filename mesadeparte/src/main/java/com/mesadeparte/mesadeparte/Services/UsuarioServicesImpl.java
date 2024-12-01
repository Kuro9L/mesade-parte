package com.mesadeparte.mesadeparte.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mesadeparte.mesadeparte.Entities.Usuario;
import com.mesadeparte.mesadeparte.Repository.UsuarioRepository;

@Service
public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    UsuarioRepository repository;

    @Override
    public List<Usuario> listUsuario() {
        return repository.findAll();
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario leeIdUsuario(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteUsuario(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Usuario leeLogin(String dni, String clave) {
        return repository.leeLogin(dni, clave);
    }

}
