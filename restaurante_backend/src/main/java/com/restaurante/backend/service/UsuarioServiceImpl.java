package com.restaurante.backend.service;

import com.restaurante.backend.model.RolEntity;
import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.repository.ReservaRepository;
import com.restaurante.backend.repository.RolRepository;
import com.restaurante.backend.repository.UsuarioRepository;
import com.restaurante.backend.security.JwtUtils;
import com.restaurante.backend.vo.LoginRequestVO;
import com.restaurante.backend.vo.LoginResponseVO;
import com.restaurante.backend.vo.UsuarioRequestVO;
import com.restaurante.backend.vo.UsuarioResponseVO;
import com.restaurante.backend.exception.AuthException;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio {@link UsuarioService}. Maneja el registro,
 * login, listado, eliminación y cambio de estado de los usuarios.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Registra un nuevo usuario en el sistema después de validar sus datos. Asigna
	 * automáticamente el rol "USER".
	 *
	 * @param vo datos del nuevo usuario
	 * @return datos públicos del usuario registrado
	 * @throws IllegalArgumentException si algún campo es inválido o ya existe el
	 *                                  email
	 */
	@Override
	public UsuarioResponseVO registrarUsuario(UsuarioRequestVO vo) {
		// Expresiones regulares
		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$";
		String nombreRegex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";

		// Validar email
		if (!vo.getEmail().matches(emailRegex)) {
			throw new IllegalArgumentException("El correo electrónico no es válido.");
		}

		// Validar contraseña
		if (!vo.getPassword().matches(passwordRegex)) {
			throw new IllegalArgumentException(
					"La contraseña debe tener al menos 6 caracteres, incluir letras y al menos un número.");
		}

		// Validar nombre
		if (!vo.getNombre().matches(nombreRegex)) {
			throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
		}

		// Validar apellidos
		if (!vo.getApellidos().matches(nombreRegex)) {
			throw new IllegalArgumentException("Los apellidos solo pueden contener letras y espacios.");
		}

		// Verificar si el email ya está registrado
		if (usuarioRepository.findByEmail(vo.getEmail()).isPresent()) {
			throw new IllegalArgumentException("El correo electrónico ya está registrado.");
		}

		// Crear el usuario
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre(vo.getNombre());
		usuario.setApellidos(vo.getApellidos());
		usuario.setEmail(vo.getEmail());
		usuario.setPassword(passwordEncoder.encode(vo.getPassword()));

		RolEntity rolUser = rolRepository.findByNombre("USER")
				.orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
		usuario.setRol(rolUser);
		usuario.setActivo(true);

		UsuarioEntity usuarioGuardado = usuarioRepository.save(usuario);

		return convertirAUsuarioVO(usuarioGuardado);
	}

	/**
	 * Realiza el login del usuario y genera un token JWT si las credenciales son
	 * válidas.
	 *
	 * @param loginVO datos de acceso
	 * @return objeto con token JWT y datos del usuario autenticado
	 * @throws AuthException si el usuario no existe, está inactivo o la contraseña
	 *                       es incorrecta
	 */
	@Override
	public LoginResponseVO login(LoginRequestVO loginVO) {
		UsuarioEntity usuario = usuarioRepository.findByEmail(loginVO.getEmail())
				.orElseThrow(() -> new AuthException("Usuario no encontrado"));

		if (!usuario.isActivo()) {
			throw new AuthException("El usuario está desactivado. Contacte con el administrador.");
		}

		if (!passwordEncoder.matches(loginVO.getPassword(), usuario.getPassword())) {
			throw new AuthException("Contraseña incorrecta");
		}

		String nombreCompleto = usuario.getNombre() + " " + usuario.getApellidos();
		String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getNombre(), usuario.getRol().getNombre());

		return new LoginResponseVO(token, nombreCompleto, usuario.getEmail(), usuario.getRol().getNombre());
	}

	/**
	 * Devuelve todos los usuarios registrados.
	 *
	 * @return lista de usuarios en formato de respuesta
	 */
	@Override
	public List<UsuarioResponseVO> listarUsuarios() {
		List<UsuarioEntity> usuarios = usuarioRepository.findAll();

		return usuarios.stream().map(this::convertirAUsuarioVO).collect(Collectors.toList());
	}

	/**
	 * Elimina un usuario y todas sus reservas asociadas.
	 *
	 * @param id identificador del usuario
	 */
	@Override
	@Transactional
	public void eliminarUsuario(Long id) {
		// 1️⃣ Borra todas las reservas del usuario
		reservaRepository.deleteByUsuarioId(id);

		// 2️⃣ Ahora sí borra el usuario
		usuarioRepository.deleteById(id);
	}

	/**
	 * Cambia el estado del usuario (activo ↔ inactivo).
	 *
	 * @param id identificador del usuario
	 * @throws RuntimeException si el usuario no existe
	 */
	@Override
	public void cambiarEstadoUsuario(Long id) {
		UsuarioEntity usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		usuario.setActivo(!usuario.isActivo()); // Cambia de true a false o viceversa
		usuarioRepository.save(usuario);
	}

	/**
	 * Convierte una entidad {@link UsuarioEntity} en un {@link UsuarioResponseVO}.
	 *
	 * @param usuario entidad de usuario
	 * @return objeto de respuesta con datos públicos
	 */
	private UsuarioResponseVO convertirAUsuarioVO(UsuarioEntity usuario) {
		UsuarioResponseVO vo = new UsuarioResponseVO();
		vo.setId(usuario.getId());
		vo.setNombre(usuario.getNombre());
		vo.setApellidos(usuario.getApellidos());
		vo.setEmail(usuario.getEmail());
		vo.setRol(usuario.getRol().getNombre());
		vo.setActivo(usuario.isActivo());
		return vo;
	}
}
