package com.mesadeparte.mesadeparte.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mesadeparte.mesadeparte.Entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("select u from Usuario u where u.dni = :dni and u.clave = :clave")
    Usuario leeLogin(@Param("dni") String dni, @Param("clave") String clave);
}
