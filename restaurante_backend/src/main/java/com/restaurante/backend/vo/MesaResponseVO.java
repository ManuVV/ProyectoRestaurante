package com.restaurante.backend.vo;

public class MesaResponseVO {

	private Long id;
	private String nombre;
	private boolean activa;

	public MesaResponseVO() {
	}

	public MesaResponseVO(Long id, String nombre, boolean activa) {
		this.id = id;
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
