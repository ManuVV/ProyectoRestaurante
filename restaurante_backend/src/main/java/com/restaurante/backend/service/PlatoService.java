package com.restaurante.backend.service;

import java.util.List;

import com.restaurante.backend.vo.PlatoRequestVO;
import com.restaurante.backend.vo.PlatoResponseVO;

/**
 * Servicio que define las operaciones de negocio relacionadas con la gestión de
 * platos. Incluye funcionalidades para crear, consultar, actualizar, eliminar y
 * cambiar el estado de los platos.
 */
public interface PlatoService {

	PlatoResponseVO crearPlato(PlatoRequestVO vo);

	List<PlatoResponseVO> listarPlatos();

	List<PlatoResponseVO> listarPlatosDisponiblesEnCarta();

	PlatoResponseVO obtenerPlatoPorId(Long id);

	PlatoResponseVO actualizarPlato(Long id, PlatoRequestVO vo);

	void eliminarPlato(Long id);

	void cambiarEstadoPlato(Long id);

}
