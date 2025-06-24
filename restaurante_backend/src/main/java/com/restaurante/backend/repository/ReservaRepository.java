package com.restaurante.backend.repository;


import com.restaurante.backend.model.ReservaEntity;
import com.restaurante.backend.model.UsuarioEntity;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link ReservaEntity}.
 * Proporciona operaciones CRUD y consultas personalizadas sobre la tabla "reservas".
 */
@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    boolean existsByMesaIdAndFechaAndHora(Long mesaId, LocalDate fecha, LocalTime hora);
   
   
    List<ReservaEntity> findByUsuario(UsuarioEntity usuario);
    List<ReservaEntity> findByFecha(LocalDate fecha);
    List<ReservaEntity> findByMesaIdAndFecha(Long idMesa, LocalDate fecha);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM ReservaEntity r WHERE r.usuario.id = :usuarioId")
    void deleteByUsuarioId(Long usuarioId);
}