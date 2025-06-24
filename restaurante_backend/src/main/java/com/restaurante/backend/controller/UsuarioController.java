package com.restaurante.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.backend.model.RolEntity;
import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.repository.RolRepository;
import com.restaurante.backend.repository.UsuarioRepository;
import com.restaurante.backend.service.UsuarioService;
import com.restaurante.backend.vo.LoginRequestVO;
import com.restaurante.backend.vo.LoginResponseVO;
import com.restaurante.backend.vo.UsuarioRequestVO;
import com.restaurante.backend.vo.UsuarioResponseVO;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de usuarios. Incluye funciones de registro,
 * login, administración y cambios de estado.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	/**
	 * Registra un nuevo usuario en el sistema. Este endpoint está abierto al
	 * público.
	 *
	 * @param usuarioVO datos del usuario a registrar
	 * @return el usuario registrado con datos públicos
	 */
	@PostMapping("/registro")
	/*
	 * public ResponseEntity<UsuarioEntity> registrar(@Valid @RequestBody
	 * UsuarioRequestVO usuarioVO) {
	 */
	public ResponseEntity<UsuarioResponseVO> registrar(@Valid @RequestBody UsuarioRequestVO usuarioVO) {
		/* UsuarioEntity nuevoUsuario = usuarioService.registrarUsuario(usuarioVO); */
		UsuarioResponseVO nuevoUsuario = usuarioService.registrarUsuario(usuarioVO);
		return ResponseEntity.ok(nuevoUsuario);
	}

	/**
	 * Permite a un usuario autenticarse con email y contraseña.
	 *
	 * @param loginVO datos de autenticación (email, password)
	 * @return respuesta con token JWT y datos del usuario autenticado
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponseVO> login(@Valid @RequestBody LoginRequestVO loginVO) {
		LoginResponseVO response = usuarioService.login(loginVO);
		return ResponseEntity.ok(response);
	}

	/**
	 * Lista todos los usuarios del sistema. Se puede limitar el acceso a este
	 * endpoint si se desea.
	 *
	 * @return lista de usuarios con datos públicos
	 */
	@GetMapping
	public ResponseEntity<List<UsuarioResponseVO>> listarUsuarios() {
		List<UsuarioResponseVO> usuarios = usuarioService.listarUsuarios();
		return ResponseEntity.ok(usuarios);
	}

	/**
	 * Elimina un usuario por su ID. Se podría proteger con rol de administrador.
	 *
	 * @param id ID del usuario a eliminar
	 * @return respuesta sin contenido
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
		usuarioService.eliminarUsuario(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Cambia el estado activo/inactivo de un usuario. Permite deshabilitar cuentas
	 * sin eliminarlas.
	 *
	 * @param id ID del usuario
	 * @return respuesta sin contenido
	 */
	@PutMapping("/estado/{id}")
	public ResponseEntity<Void> cambiarEstado(@PathVariable Long id) {
		usuarioService.cambiarEstadoUsuario(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Registra un usuario con rol ADMIN de forma manual (sin pasar por la lógica
	 * estándar de registro).
	 *
	 * @param vo datos del nuevo administrador
	 * @return mensaje de éxito
	 */
	@PostMapping("/registro-admin")
	public ResponseEntity<?> registrarAdmin(@RequestBody UsuarioRequestVO vo) {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre(vo.getNombre());
		usuario.setApellidos(vo.getApellidos());
		usuario.setEmail(vo.getEmail());
		usuario.setPassword(passwordEncoder.encode(vo.getPassword()));
		usuario.setActivo(true);

		RolEntity rolAdmin = rolRepository.findByNombre("ADMIN")
				.orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
		usuario.setRol(rolAdmin);

		usuarioRepository.save(usuario);
		return ResponseEntity.ok("Usuario admin registrado");
	}

}
