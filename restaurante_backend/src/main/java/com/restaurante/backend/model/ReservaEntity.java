package com.restaurante.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad JPA que representa una reserva en el sistema del restaurante. Cada
 * reserva está asociada a un usuario, una mesa, y contiene fecha, hora y número
 * de personas.
 */
@Entity
@Table(name = "reservas")
public class ReservaEntity {

	/**
	 * Identificador único de la reserva. Se genera automáticamente con estrategia
	 * IDENTITY.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de la reserva.
	 */
	@Column(nullable = false)
	private String nombre;

	/**
	 * Fecha de la reserva.
	 */
	@Column(nullable = false)
	private LocalDate fecha;

	/**
	 * Hora de la reserva.
	 */
	@Column(nullable = false)
	private LocalTime hora;

	/**
	 * Número de personas para la reserva.
	 */
	@Column(nullable = false)
	private int numeroPersonas;

	/**
	 * Usuario que ha realizado la reserva. Relación muchos a uno.
	 */
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioEntity usuario;

	/**
	 * Mesa asignada para la reserva. Relación muchos a uno (obligatoria).
	 */
	@ManyToOne
	@JoinColumn(name = "mesa_id", nullable = false)
	private MesaEntity mesa;

	public ReservaEntity() {
	}

	public ReservaEntity(Long id, String nombre, LocalDate fecha, LocalTime hora, int numeroPersonas,
			UsuarioEntity usuario, MesaEntity mesa) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.hora = hora;
		this.numeroPersonas = numeroPersonas;
		this.usuario = usuario;
		this.mesa = mesa;
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

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public MesaEntity getMesa() {
		return mesa;
	}

	public void setMesa(MesaEntity mesa) {
		this.mesa = mesa;
	}

}
