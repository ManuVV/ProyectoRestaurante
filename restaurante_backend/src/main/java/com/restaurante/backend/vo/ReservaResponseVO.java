package com.restaurante.backend.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaResponseVO {
	
	 	private Long id;
	    private String nombre;
	    private LocalDate fecha;
	    private LocalTime hora;
	    private int numeroPersonas;
	    private Long idMesa;
	    private String nombreMesa; // 
	    private String nombreUsuario;
	    private String emailUsuario;
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
		public LocalDate getFecha() {
			return fecha;
		}
		public void setFecha(LocalDate fecha) {
			this.fecha = fecha;
		}
		public LocalTime getHora() {
			return hora;
		}
		public void setHora(LocalTime hora) {
			this.hora = hora;
		}
		public int getNumeroPersonas() {
			return numeroPersonas;
		}
		public void setNumeroPersonas(int numeroPersonas) {
			this.numeroPersonas = numeroPersonas;
		}
		public Long getIdMesa() {
			return idMesa;
		}
		public void setIdMesa(Long idMesa) {
			this.idMesa = idMesa;
		}
		public String getNombreMesa() {
			return nombreMesa;
		}
		public void setNombreMesa(String nombreMesa) {
			this.nombreMesa = nombreMesa;
		}
		public String getNombreUsuario() {
			return nombreUsuario;
		}
		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}
		public String getEmailUsuario() {
			return emailUsuario;
		}
		public void setEmailUsuario(String emailUsuario) {
			this.emailUsuario = emailUsuario;
		}

	    
	    
	    
}
