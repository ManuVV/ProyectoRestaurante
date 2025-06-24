package com.restaurante.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.backend.model.MesaEntity;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.vo.MesaRequestVO;
import com.restaurante.backend.vo.MesaResponseVO;

/**
 * Implementación del servicio {@link MesaService} que gestiona la lógica de
 * negocio relacionada con las mesas del restaurante.
 */
@Service
public class MesaServiceImpl implements MesaService {

	@Autowired
	private MesaRepository mesaRepository;

	/**
	 * Obtiene todas las mesas del sistema.
	 *
	 * @return lista de mesas en formato de respuesta
	 */
	@Override
	public List<MesaResponseVO> listarMesas() {
		return mesaRepository.findAll().stream().map(this::toVO).toList();
	}

	/**
	 * Crea una nueva mesa, activándola por defecto.
	 *
	 * @param vo objeto con los datos de la nueva mesa
	 * @return objeto de respuesta con los datos de la mesa creada
	 */
	@Override
	public MesaResponseVO crearMesa(MesaRequestVO vo) {
		MesaEntity mesa = new MesaEntity();
		mesa.setNombre(vo.getNombre());
		mesa.setActiva(true); // siempre activa al crear

		MesaEntity saved = mesaRepository.save(mesa);
		return toVO(saved);
	}

	/**
	 * Elimina una mesa del sistema por su ID.
	 *
	 * @param id identificador de la mesa a eliminar
	 * @throws RuntimeException si la mesa no existe
	 */
	@Override
	public void eliminarMesa(Long id) {
		if (!mesaRepository.existsById(id)) {
			throw new RuntimeException("Mesa no encontrada");
		}
		mesaRepository.deleteById(id);
	}

	/**
	 * Cambia el estado de la mesa (activa ↔ inactiva).
	 *
	 * @param id identificador de la mesa cuyo estado se desea modificar
	 * @throws RuntimeException si la mesa no existe
	 */
	@Override
	public void cambiarEstadoMesa(Long id) {
		MesaEntity mesa = mesaRepository.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
		mesa.setActiva(!mesa.isActiva());
		mesaRepository.save(mesa);
	}

	/**
	 * Convierte una entidad {@link MesaEntity} a un objeto {@link MesaResponseVO}.
	 *
	 * @param entity entidad mesa
	 * @return objeto de respuesta para el frontend
	 */
	private MesaResponseVO toVO(MesaEntity entity) {
		return new MesaResponseVO(entity.getId(), entity.getNombre(), entity.isActiva());
	}
}
