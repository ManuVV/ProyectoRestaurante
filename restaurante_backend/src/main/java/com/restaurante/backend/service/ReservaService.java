package com.restaurante.backend.service;


import com.restaurante.backend.model.ReservaEntity;
import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.vo.ReservaRequestVO;
import com.restaurante.backend.vo.ReservaResponseVO;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio que define la lógica de negocio para la gestión de reservas en el restaurante.
 * Permite crear, consultar y cancelar reservas, tanto por parte del usuario como del administrador.
 */
public interface ReservaService {
    ReservaResponseVO crearReserva(ReservaRequestVO vo, UsuarioEntity usuario);
    List<ReservaResponseVO> listarReservas();
    List<ReservaResponseVO> listarPorUsuario(UsuarioEntity usuario);
    
	/*
	 * List<ReservaEntity> listarPorFecha(LocalDate fecha);
	 */    List<ReservaEntity> listarPorFechaYPorMesa(Long idMesa, LocalDate fecha);
    void eliminarReservaPorAdmin(Long id);
    void cancelarReserva(Long id, UsuarioEntity usuario);
}
