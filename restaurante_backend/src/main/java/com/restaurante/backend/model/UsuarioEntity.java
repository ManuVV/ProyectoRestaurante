package com.restaurante.backend.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un usuario dentro del sistema. Cada usuario tiene
 * credenciales, datos personales, un rol y un estado de actividad.
 */
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

	/**
	 * Identificador único del usuario. Se genera automáticamente con estrategia
	 * IDENTITY.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Dirección de correo electrónico del usuario. Es única, obligatoria y con un
	 * máximo de 50 caracteres.
	 */
	@Column(nullable = false, unique = true, length = 50)
	private String email;

	/**
	 * Contraseña del usuario. Campo obligatorio.
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * Nombre del usuario. Campo obligatorio con un máximo de 50 caracteres.
	 */
	@Column(nullable = false, length = 50)
	private String nombre;

	/**
	 * Apellidos del usuario. Campo obligatorio con un máximo de 50 caracteres.
	 */
	@Column(nullable = false, length = 50)
	private String apellidos;

	/**
	 * Rol asignado al usuario.Relación muchos a uno.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rol_id", nullable = false)
	private RolEntity rol;

	/**
	 * Indica si la cuenta del usuario está activa o no. Por defecto, es true
	 * (activo).
	 */
	@Column(nullable = false)
	private boolean activo = true;

	public UsuarioEntity() {
	}

	public UsuarioEntity(Long id, String email, String password, String nombre, String apellidos, RolEntity rol,
			boolean activo) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.rol = rol;
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public RolEntity getRol() {
		return rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
