package com.restaurante.backend.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa una categoría de platos en el sistema del
 * restaurante. Cada categoría tiene un nombre único y un identificador
 * autogenerado.
 */
@Entity
@Table(name = "categorias")
public class CategoriaEntity {

	/**
	 * Identificador único de la categoría. Se genera automáticamente.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de la categoría. Es obligatorio (no puede ser nulo) y debe ser único
	 * en la base de datos.
	 */
	@Column(nullable = false, unique = true)
	private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
