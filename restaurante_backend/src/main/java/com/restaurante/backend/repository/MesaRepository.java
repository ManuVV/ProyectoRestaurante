package com.restaurante.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.model.MesaEntity;

/**
 * Repositorio JPA para la entidad {@link MesaEntity}.
 * Proporciona operaciones CRUD y consultas personalizadas sobre la tabla "mesas".
 */
@Repository
public interface MesaRepository extends JpaRepository<MesaEntity, Long> {
	
	 boolean existsByNombre(String nombre); // Para comprobar duplicados
}
