package com.restaurante.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.backend.model.CategoriaEntity;


/**
 * Repositorio JPA para la entidad {@link CategoriaEntity}.
 * Proporciona operaciones CRUD y consultas personalizadas sobre la tabla "categorias".
 */
@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
