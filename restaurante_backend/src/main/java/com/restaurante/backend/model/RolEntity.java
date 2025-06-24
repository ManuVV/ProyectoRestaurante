package com.restaurante.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Entidad JPA que representa un rol dentro del sistema de usuarios.
 * Los roles determinan los permisos que tiene un usuario, por ejemplo "USER" o "ADMIN".
 */
@Entity
@Table(name = "roles")
public class RolEntity {
	
    /**
     * Identificador único del rol.
     * Se genera automáticamente con estrategia IDENTITY.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    /**
     * Nombre del rol. Debe ser único y no puede ser nulo.
     */
	@Column(unique = true, nullable = false)
	private String nombre; // Ejemplo: "USER", "ADMIN"

	public RolEntity() {
	}

	public RolEntity(String nombre) {
		this.nombre = nombre;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
