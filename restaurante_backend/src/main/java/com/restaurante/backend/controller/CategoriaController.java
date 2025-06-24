package com.restaurante.backend.controller;

import com.restaurante.backend.service.CategoriaService;
import com.restaurante.backend.vo.CategoriaRequestVO;
import com.restaurante.backend.vo.CategoriaResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de categorías de platos en la carta del
 * restaurante. Proporciona endpoints para listar, crear, actualizar y eliminar
 * categorías.
 */
@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	/**
	 * Obtiene la lista completa de categorías disponibles.
	 *
	 * @return una lista de objetos CategoriaResponseVO envuelta en ResponseEntity
	 */
	@GetMapping
	public ResponseEntity<List<CategoriaResponseVO>> listarCategorias() {
		return ResponseEntity.ok(categoriaService.listarCategorias());
	}

	/**
	 * Obtiene una categoría específica por su ID.
	 *
	 * @param id identificador de la categoría
	 * @return el objeto CategoriaResponseVO correspondiente al ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponseVO> obtenerCategoria(@PathVariable Long id) {
		return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
	}

	/**
	 * Crea una nueva categoría. Solo accesible por usuarios con rol ADMIN.
	 *
	 * @param vo objeto con los datos necesarios para crear una categoría
	 * @return la categoría creada con su ID generado
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoriaResponseVO> crearCategoria(@RequestBody CategoriaRequestVO vo) {
		return ResponseEntity.ok(categoriaService.crearCategoria(vo));
	}

	/**
	 * Actualiza una categoría existente. Solo accesible por usuarios con rol ADMIN.
	 *
	 * @param id ID de la categoría a actualizar
	 * @param vo objeto con los nuevos datos para la categoría
	 * @return la categoría actualizada
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoriaResponseVO> actualizarCategoria(@PathVariable Long id,
			@RequestBody CategoriaRequestVO vo) {
		return ResponseEntity.ok(categoriaService.actualizarCategoria(id, vo));
	}

	/**
	 * Elimina una categoría existente. Solo accesible por usuarios con rol ADMIN.
	 *
	 * @param id ID de la categoría a eliminar
	 * @return una respuesta vacía con código 204 No Content
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
		categoriaService.eliminarCategoria(id);
		return ResponseEntity.noContent().build();
	}
}
