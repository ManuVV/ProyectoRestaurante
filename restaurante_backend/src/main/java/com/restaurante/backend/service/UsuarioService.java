package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.vo.LoginRequestVO;
import com.restaurante.backend.vo.LoginResponseVO;
import com.restaurante.backend.vo.UsuarioRequestVO;
import com.restaurante.backend.vo.UsuarioResponseVO;

/**
 * Servicio que define las operaciones de negocio relacionadas con la gestión de usuarios.
 * Incluye funcionalidades para registro, login, listado, eliminación y activación/desactivación de cuentas.
 */
public interface UsuarioService {
	
	/* UsuarioEntity registrarUsuario( UsuarioResponseVO vo); */
	 UsuarioResponseVO registrarUsuario(UsuarioRequestVO vo);
	LoginResponseVO login(LoginRequestVO loginVO);
	List<UsuarioResponseVO> listarUsuarios();
	void eliminarUsuario(Long id);
	void cambiarEstadoUsuario(Long id);
}
