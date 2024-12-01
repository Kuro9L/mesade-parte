package com.mesadeparte.mesadeparte.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mesadeparte.mesadeparte.Entities.Seguimiento;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long>{

}
