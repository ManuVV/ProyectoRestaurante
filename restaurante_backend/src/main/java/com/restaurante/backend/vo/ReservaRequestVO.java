package com.restaurante.backend.vo;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequestVO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @Min(value = 1, message = "Debe haber al menos una persona")
    @Max(value = 8, message = "No se permiten más de 8 personas por reserva")
    private int numeroPersonas;

    @NotNull(message = "Debe especificar una mesa")
    private Long idMesa;

    public ReservaRequestVO() {}

    public ReservaRequestVO(String nombre, LocalDate fecha, LocalTime hora, int numeroPersonas, Long idMesa) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroPersonas = numeroPersonas;
        this.idMesa = idMesa;
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

    // Getters y setters
}
