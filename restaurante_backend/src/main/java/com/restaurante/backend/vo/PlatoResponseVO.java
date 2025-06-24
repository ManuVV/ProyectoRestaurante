package com.restaurante.backend.vo;

import java.math.BigDecimal;

public class PlatoResponseVO {


    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen;
    private boolean disponibleEnCarta;
    private String nombreCategoria;
    
	public PlatoResponseVO(Long id, String nombre, String descripcion, BigDecimal precio, String imagen,
			boolean disponibleEnCarta, String nombreCategoria) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.imagen = imagen;
		this.disponibleEnCarta = disponibleEnCarta;
		 this.nombreCategoria = nombreCategoria;
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

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
    
    
}
