package com.restaurante.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad JPA que representa una mesa en el restaurante. Cada mesa tiene un
 * nombre único y puede estar activa o inactiva.
 */
@Entity
@Table(name = "mesas", uniqueConstraints = { @UniqueConstraint(columnNames = "nombre") // Evita duplicados a nivel de BD
})
public class MesaEntity {

	/**
	 * Identificador único de la mesa. Se genera automáticamente.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de la mesa. Es obligatorio y debe ser único.
	 */
	@NotBlank(message = "El nombre de la mesa es obligatorio")
	@Column(nullable = false, unique = true)
	private String nombre;

	/**
	 * Indica si la mesa está activa. Por ejemplo, se puede usar para ocultar mesas
	 * temporalmente.
	 */
	private boolean activa;
	
	
	public MesaEntity() {
	}
	
	public MesaEntity(String nombre, boolean activa) {
		this.nombre = nombre;
		this.activa = activa;
	}

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

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
}
