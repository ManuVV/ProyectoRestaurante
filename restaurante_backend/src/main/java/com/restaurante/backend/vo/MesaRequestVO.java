package com.restaurante.backend.vo;

public class MesaRequestVO {
	private String nombre;

	public MesaRequestVO(String nombre) {
		this.nombre = nombre;
	}
	
	public MesaRequestVO() {
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
}
