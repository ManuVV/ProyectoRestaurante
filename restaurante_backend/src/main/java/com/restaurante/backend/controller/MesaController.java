package com.restaurante.backend.controller;

import com.restaurante.backend.service.MesaService;
import com.restaurante.backend.vo.MesaRequestVO;
import com.restaurante.backend.vo.MesaResponseVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de mesas en el restaurante. Permite obtener,
 * crear, eliminar y cambiar el estado de las mesas.
 */
@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

	@Autowired
	private MesaService mesaService;

	/**
	 * Obtiene la lista de todas las mesas del sistema. Este endpoint está abierto
	 * al público (no requiere autenticación).
	 *
	 * @return lista de objetos MesaResponseVO
	 */
	@GetMapping
	public ResponseEntity<List<MesaResponseVO>> obtenerTodas() {
		return ResponseEntity.ok(mesaService.listarMesas());
	}

	/**
	 * Crea una nueva mesa en el sistema. Solo accesible para usuarios con rol
	 * ADMIN.
	 *
	 * @param vo objeto que contiene los datos necesarios para crear la mesa
	 * @return la mesa creada, con su información correspondiente
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<MesaResponseVO> crear(@Valid @RequestBody MesaRequestVO vo) {
		return ResponseEntity.ok(mesaService.crearMesa(vo));
	}

	/**
	 * Elimina una mesa del sistema según su ID. Solo accesible para usuarios con
	 * rol ADMIN.
	 *
	 * @param id identificador de la mesa a eliminar
	 * @return respuesta vacía con estado 204 (No Content)
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		mesaService.eliminarMesa(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Cambia el estado de una mesa (por ejemplo, activa/inactiva). Solo accesible
	 * para usuarios con rol ADMIN.
	 *
	 * @param id identificador de la mesa cuyo estado se desea cambiar
	 * @return respuesta con estado 200 (OK) sin contenido
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/estado/{id}")
	public ResponseEntity<Void> cambiarEstado(@PathVariable Long id) {
		mesaService.cambiarEstadoMesa(id);
		return ResponseEntity.ok().build();
	}
}
