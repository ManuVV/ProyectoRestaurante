package com.restaurante.backend.vo;

import java.math.BigDecimal;

public class PlatoRequestVO {
	

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen; // nombre del archivo (opcional si luego subimos por separado)
    private boolean disponibleEnCarta;
    private Long idCategoria;
    
    
    
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
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
