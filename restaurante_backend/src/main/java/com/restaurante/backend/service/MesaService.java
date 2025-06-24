package com.restaurante.backend.service;


import com.restaurante.backend.vo.MesaRequestVO;
import com.restaurante.backend.vo.MesaResponseVO;

import java.util.List;

/**
 * Servicio que define las operaciones de negocio relacionadas con la gestión de mesas.
 * Incluye funcionalidades para listar, crear, eliminar y cambiar el estado de las mesas.
 */
public interface MesaService {
	List<MesaResponseVO> listarMesas();
	MesaResponseVO crearMesa(MesaRequestVO vo);
	void eliminarMesa(Long id);
	void cambiarEstadoMesa(Long id);
}