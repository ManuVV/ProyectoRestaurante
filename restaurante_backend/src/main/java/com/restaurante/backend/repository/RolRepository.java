package com.restaurante.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.model.RolEntity;

/**
 * Repositorio JPA para la entidad {@link RolEntity}.
 * Permite acceder a los roles definidos en el sistema, como "ADMIN" o "USER".
 */
@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {
	 Optional<RolEntity> findByNombre(String nombre);
	

}
