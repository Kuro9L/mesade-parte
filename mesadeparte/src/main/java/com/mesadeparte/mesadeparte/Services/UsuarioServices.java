package com.mesadeparte.mesadeparte.Services;

import java.util.List;

import com.mesadeparte.mesadeparte.Entities.Usuario;

public interface UsuarioServices {
    List<Usuario> listUsuario();

    Usuario saveUsuario(Usuario usuario);

    Usuario leeIdUsuario(Long id);

    void deleteUsuario(Long id);

    Usuario leeLogin(String dni, String clave);
}
