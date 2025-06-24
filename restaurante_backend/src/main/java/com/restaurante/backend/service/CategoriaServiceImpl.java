package com.restaurante.backend.service;

import com.restaurante.backend.model.CategoriaEntity;
import com.restaurante.backend.repository.CategoriaRepository;
import com.restaurante.backend.vo.CategoriaRequestVO;
import com.restaurante.backend.vo.CategoriaResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link CategoriaService}. Contiene la lógica de
 * negocio relacionada con la gestión de categorías de platos.
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 * Crea una nueva categoría si no existe otra con el mismo nombre (ignorando
	 * mayúsculas/minúsculas).
	 *
	 * @param vo objeto con los datos de la categoría a crear
	 * @return categoría creada en formato de respuesta
	 * @throws IllegalArgumentException si ya existe una categoría con el mismo nombre
	 *                                  
	 */
	@Override
	public CategoriaResponseVO crearCategoria(CategoriaRequestVO vo) {
		if (categoriaRepository.existsByNombreIgnoreCase(vo.getNombre())) {
			throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
		}

		CategoriaEntity categoria = new CategoriaEntity();
		categoria.setNombre(vo.getNombre());
		categoria = categoriaRepository.save(categoria);

		return new CategoriaResponseVO(categoria.getId(), categoria.getNombre());
	}

	/**
	 * Lista todas las categorías existentes en la base de datos.
	 *
	 * @return lista de objetos de respuesta de categoría
	 */
	@Override
	public List<CategoriaResponseVO> listarCategorias() {
		return categoriaRepository.findAll().stream().map(cat -> new CategoriaResponseVO(cat.getId(), cat.getNombre()))
				.toList();
	}

	/**
	 * Obtiene una categoría a partir de su ID.
	 *
	 * @param id identificador de la categoría
	 * @return objeto de respuesta con los datos de la categoría encontrada
	 * @throws RuntimeException si la categoría no existe
	 */

	@Override
	public CategoriaResponseVO obtenerCategoriaPorId(Long id) {
		CategoriaEntity categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
		return new CategoriaResponseVO(categoria.getId(), categoria.getNombre());
	}

	/**
	 * Actualiza una categoría existente con los nuevos datos proporcionados.
	 *
	 * @param id identificador de la categoría a actualizar
	 * @param vo objeto con el nuevo nombre de la categoría
	 * @return objeto de respuesta con los datos actualizados
	 * @throws RuntimeException si la categoría no existe
	 */
	@Override
	public CategoriaResponseVO actualizarCategoria(Long id, CategoriaRequestVO vo) {
		CategoriaEntity categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

		categoria.setNombre(vo.getNombre());
		categoria = categoriaRepository.save(categoria);
		return new CategoriaResponseVO(categoria.getId(), categoria.getNombre());
	}

	/**
	 * Elimina una categoría por su ID.
	 *
	 * @param id identificador de la categoría a eliminar
	 * @throws RuntimeException si la categoría no existe
	 */
	@Override
	public void eliminarCategoria(Long id) {
		if (!categoriaRepository.existsById(id)) {
			throw new RuntimeException("Categoría no encontrada");
		}
		categoriaRepository.deleteById(id);
	}
}
