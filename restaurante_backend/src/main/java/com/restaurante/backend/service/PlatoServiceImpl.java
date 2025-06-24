package com.restaurante.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.backend.model.CategoriaEntity;
import com.restaurante.backend.model.PlatoEntity;
import com.restaurante.backend.repository.CategoriaRepository;
import com.restaurante.backend.repository.PlatoRepository;
import com.restaurante.backend.vo.PlatoRequestVO;
import com.restaurante.backend.vo.PlatoResponseVO;

/**
 * Implementación del servicio {@link PlatoService} que contiene la lógica de
 * negocio para la gestión de platos del restaurante.
 */
@Service
public class PlatoServiceImpl implements PlatoService {

	@Autowired
	private PlatoRepository platoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 * Crea un nuevo plato a partir de los datos proporcionados. Asocia el plato a
	 * una categoría existente.
	 *
	 * @param vo objeto con los datos del nuevo plato
	 * @return plato creado en formato de respuesta
	 * @throws RuntimeException si la categoría especificada no existe
	 */
	@Override
	public PlatoResponseVO crearPlato(PlatoRequestVO vo) {
		PlatoEntity plato = new PlatoEntity();
		plato.setNombre(vo.getNombre());
		plato.setDescripcion(vo.getDescripcion());
		plato.setPrecio(vo.getPrecio());
		plato.setImagen(vo.getImagen());
		plato.setDisponibleEnCarta(vo.isDisponibleEnCarta());

		// Buscar la categoría por ID y asignarla al plato
		CategoriaEntity categoria = categoriaRepository.findById(vo.getIdCategoria())
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

		plato.setCategoria(categoria);

		return toVO(platoRepository.save(plato));
	}

	/**
	 * Devuelve todos los platos, incluyendo los no visibles en la carta.
	 *
	 * @return lista de platos en formato de respuesta
	 */
	@Override
	public List<PlatoResponseVO> listarPlatos() {
		return platoRepository.findAll().stream().map(this::toVO).toList();
	}

	/**
	 * Devuelve solo los platos que están marcados como disponibles en la carta.
	 *
	 * @return lista de platos disponibles en carta
	 */
	@Override
	public List<PlatoResponseVO> listarPlatosDisponiblesEnCarta() {
		return platoRepository.findByDisponibleEnCartaTrue().stream().map(this::toVO).toList();
	}

	/**
	 * Obtiene los detalles de un plato por su ID.
	 *
	 * @param id identificador del plato
	 * @return objeto de respuesta con los datos del plato
	 * @throws RuntimeException si el plato no existe
	 */
	@Override
	public PlatoResponseVO obtenerPlatoPorId(Long id) {
		PlatoEntity plato = platoRepository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado"));
		return toVO(plato);
	}

	/**
	 * Actualiza los datos de un plato existente.
	 *
	 * @param id identificador del plato a actualizar
	 * @param vo objeto con los nuevos datos
	 * @return objeto de respuesta actualizado
	 * @throws RuntimeException si el plato no existe
	 */
	@Override
	public PlatoResponseVO actualizarPlato(Long id, PlatoRequestVO vo) {
		PlatoEntity plato = platoRepository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado"));

		plato.setNombre(vo.getNombre());
		plato.setDescripcion(vo.getDescripcion());
		plato.setPrecio(vo.getPrecio());
		plato.setImagen(vo.getImagen());
		plato.setDisponibleEnCarta(vo.isDisponibleEnCarta());

		return toVO(platoRepository.save(plato));
	}

	/**
	 * Elimina un plato por su identificador.
	 *
	 * @param id identificador del plato
	 * @throws RuntimeException si el plato no existe
	 */
	@Override
	public void eliminarPlato(Long id) {
		if (!platoRepository.existsById(id)) {
			throw new RuntimeException("Plato no encontrado");
		}
		platoRepository.deleteById(id);

	}

	/**
	 * Cambia el estado de visibilidad del plato en la carta (activo ↔ inactivo).
	 *
	 * @param id identificador del plato
	 * @throws RuntimeException si el plato no existe
	 */
	@Override
	public void cambiarEstadoPlato(Long id) {
		PlatoEntity plato = platoRepository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado"));

		plato.setDisponibleEnCarta(!plato.isDisponibleEnCarta()); // 🔄 cambia de true a false y viceversa
		platoRepository.save(plato);
	}

	/**
	 * Convierte una entidad {@link PlatoEntity} a un objeto de respuesta
	 * {@link PlatoResponseVO}.
	 *
	 * @param entity entidad del plato
	 * @return objeto de respuesta para el frontend
	 */
	private PlatoResponseVO toVO(PlatoEntity entity) {
		String nombreCategoria = (entity.getCategoria() != null) ? entity.getCategoria().getNombre() : null;

		return new PlatoResponseVO(entity.getId(), entity.getNombre(), entity.getDescripcion(), entity.getPrecio(),
				entity.getImagen(), entity.isDisponibleEnCarta(), nombreCategoria);
	}

}
