package com.restaurante.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un plato dentro del sistema del restaurante. Cada
 * plato pertenece a una categoría, tiene un precio y puede estar o no visible
 * en la carta.
 */
@Entity
@Table(name = "platos")
public class PlatoEntity {

	/**
	 * Identificador único del plato. Se genera automáticamente.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre del plato. Campo obligatorio.
	 */
	@Column(nullable = false)
	private String nombre;

	/**
	 * Descripción detallada del plato (hasta 1000 caracteres).
	 */
	@Column(length = 1000)
	private String descripcion;

	/**
	 * Precio del plato, representado como BigDecimal para mayor precisión.
	 */
	@Column(nullable = false)
	private BigDecimal precio;

	/**
	 * URL de la imagen asociada al plato.
	 */
	private String imagen;

	/**
	 * Indica si el plato está disponible para ser mostrado en la carta. Por
	 * defecto, es verdadero.
	 */
	@Column(nullable = false)
	private boolean disponibleEnCarta = true; // 👈 por defecto disponible

	/**
	 * Categoría a la que pertenece el plato. Relación muchos a uno con la entidad
	 * CategoriaEntity.
	 */
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriaEntity categoria;

	public PlatoEntity() {
	}

	public PlatoEntity(Long id, String nombre, String descripcion, BigDecimal precio, String imagen,
			CategoriaEntity categoria, boolean disponibleEnCarta) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.imagen = imagen;
		this.disponibleEnCarta = disponibleEnCarta;
		this.categoria = categoria;
	}

	public CategoriaEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isDisponibleEnCarta() {
		return disponibleEnCarta;
	}

	public void setDisponibleEnCarta(boolean disponibleEnCarta) {
		this.disponibleEnCarta = disponibleEnCarta;
	}

}
