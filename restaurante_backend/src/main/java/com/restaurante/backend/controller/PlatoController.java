package com.restaurante.backend.controller;

import com.restaurante.backend.service.PlatoService;
import com.restaurante.backend.vo.PlatoRequestVO;
import com.restaurante.backend.vo.PlatoResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de platos en el sistema del restaurante.
 * Proporciona endpoints para operaciones CRUD, cambiar estado y obtener platos
 * disponibles para la carta.
 */
@RestController
@RequestMapping("/api/platos")
@CrossOrigin(origins = "*")
public class PlatoController {

	@Autowired
	private PlatoService platoService;

	/**
	 * Obtiene todos los platos, sin filtrar por disponibilidad.
	 *
	 * @return lista completa de platos (administración)
	 */
	@GetMapping
	public ResponseEntity<List<PlatoResponseVO>> listarPlatos() {
		return ResponseEntity.ok(platoService.listarPlatos());
	}

	/**
	 * Obtiene solo los platos marcados como disponibles en carta. Este endpoint es
	 * público, para mostrar la carta digital.
	 *
	 * @return lista de platos disponibles en carta
	 */
	@GetMapping("/carta")
	public ResponseEntity<List<PlatoResponseVO>> obtenerPlatosDisponiblesEnCarta() {
		return ResponseEntity.ok(platoService.listarPlatosDisponiblesEnCarta());
	}

	/**
	 * Obtiene un plato por su ID.
	 *
	 * @param id identificador del plato
	 * @return datos del plato correspondiente
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PlatoResponseVO> obtenerPlato(@PathVariable Long id) {
		return ResponseEntity.ok(platoService.obtenerPlatoPorId(id));
	}

	/**
	 * Crea un nuevo plato. Solo accesible para usuarios con rol ADMIN.
	 *
	 * @param vo datos del nuevo plato
	 * @return plato creado con su ID generado
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PlatoResponseVO> crearPlato(@RequestBody PlatoRequestVO vo) {
		return ResponseEntity.ok(platoService.crearPlato(vo));
	}

	/**
	 * Actualiza un plato existente. Solo accesible para usuarios con rol ADMIN.
	 *
	 * @param id identificador del plato a actualizar
	 * @param vo nuevos datos para el plato
	 * @return plato actualizado
	 */
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PlatoResponseVO> actualizarPlato(@PathVariable Long id, @RequestBody PlatoRequestVO vo) {
		return ResponseEntity.ok(platoService.actualizarPlato(id, vo));
	}

	/**
	 * Elimina un plato del sistema. Solo accesible para usuarios con rol ADMIN.
	 *
	 * @param id identificador del plato a eliminar
	 * @return respuesta vacía con estado 204
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> eliminarPlato(@PathVariable Long id) {
		platoService.eliminarPlato(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Cambia el estado de visibilidad del plato (por ejemplo, activo/inactivo en
	 * carta). Solo accesible para usuarios con rol ADMIN.
	 *
	 * @param id identificador del plato cuyo estado se desea cambiar
	 * @return respuesta vacía con estado 204
	 */
	@PutMapping("/estado/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> cambiarEstado(@PathVariable Long id) {
		platoService.cambiarEstadoPlato(id);
		return ResponseEntity.noContent().build();
	}
}
