package com.mesadeparte.mesadeparte.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mesadeparte.mesadeparte.Entities.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long>{

}
