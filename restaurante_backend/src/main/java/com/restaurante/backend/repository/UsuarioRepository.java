package com.restaurante.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.model.UsuarioEntity;

/**
 * Repositorio JPA para la entidad {@link UsuarioEntity}.
 * Permite realizar operaciones CRUD y consultas personalizadas sobre los usuarios del sistema.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	
	Optional<UsuarioEntity> findByEmail(String email);
}
