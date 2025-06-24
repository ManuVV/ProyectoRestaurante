package com.restaurante.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.restaurante.backend.model.PlatoEntity;

/**
 * Repositorio JPA para la entidad {@link PlatoEntity}.
 * Proporciona operaciones CRUD y consultas personalizadas sobre la tabla "platos".
 */
@Repository
public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {
	
	List<PlatoEntity> findByDisponibleEnCartaTrue();

}
