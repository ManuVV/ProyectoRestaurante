package com.restaurante.backend.controller;

/*import com.restaurante.backend.model.MesaEntity;*/
import com.restaurante.backend.model.ReservaEntity;
import com.restaurante.backend.model.UsuarioEntity;
/*import com.restaurante.backend.repository.ReservaRepository;*/
import com.restaurante.backend.service.ReservaService;
import com.restaurante.backend.vo.ReservaRequestVO;
import com.restaurante.backend.vo.ReservaResponseVO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Controlador REST para la gestión de reservas en el restaurante. Permite a
 * usuarios y administradores crear, consultar y cancelar reservas.
 */
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

	private static final List<String> TRAMOS_HORARIOS = Arrays.asList("13:00", "13:30", "14:00", "14:30", "15:00",
			"15:30", "20:00", "20:30", "21:00", "21:30");

	@Autowired
	private ReservaService reservaService;

	/**
	 * Permite a un usuario autenticado crear una reserva.
	 *
	 * @param vo      objeto con los datos de la reserva
	 * @param usuario usuario autenticado (inyectado automáticamente por Spring
	 *                Security)
	 * @return la reserva creada
	 */
	@PostMapping
	public ResponseEntity<ReservaResponseVO> crearReserva(@Valid @RequestBody ReservaRequestVO vo,
			@AuthenticationPrincipal UsuarioEntity usuario) {
		ReservaResponseVO creada = reservaService.crearReserva(vo, usuario);
		System.out.println("Nombre recibido en reserva: " + vo.getNombre());
		return ResponseEntity.ok(creada);
	}

	/**
	 * Devuelve la lista completa de reservas. Solo accesible por administradores.
	 *
	 * @return lista de todas las reservas del sistema
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservaResponseVO>> listarReservas() {
		return ResponseEntity.ok(reservaService.listarReservas());
	}
	
	

	/**
	 * Permite a un administrador eliminar cualquier reserva.
	 *
	 * @param id ID de la reserva a eliminar
	 * @return respuesta vacía con estado 204 (No Content)
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarReservaComoAdmin(@PathVariable Long id) {
		reservaService.eliminarReservaPorAdmin(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Devuelve la lista de reservas del usuario autenticado. Solo accesible por
	 * usuarios con rol USER.
	 *
	 * @param usuario usuario autenticado
	 * @return lista de reservas del usuario
	 */
	@GetMapping("/mis-reservas")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<ReservaResponseVO>> misReservas(@AuthenticationPrincipal UsuarioEntity usuario) {
		return ResponseEntity.ok(reservaService.listarPorUsuario(usuario));
	}

	/**
	 * Permite a un usuario cancelar una de sus propias reservas.
	 *
	 * @param id      ID de la reserva a cancelar
	 * @param usuario usuario autenticado que realiza la cancelación
	 * @return respuesta con estado 200 (OK)
	 */
	@DeleteMapping("/mis-reservas/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> cancelarReserva(@PathVariable Long id, @AuthenticationPrincipal UsuarioEntity usuario) {
		reservaService.cancelarReserva(id, usuario);
		return ResponseEntity.ok().build();
	}
	
    /**
     * Devuelve la lista de horarios disponibles para una fecha específica.
     * Este endpoint es público y se usa para mostrar la disponibilidad en el formulario de reserva.
     *
     * @param fecha fecha en formato ISO (yyyy-MM-dd)
     * @return lista de horarios disponibles para esa fecha
     */
	@GetMapping("/horarios-disponibles")
	public ResponseEntity<List<String>> obtenerHorariosDisponibles(
	        @RequestParam("fecha") String fecha,
	        @RequestParam("idMesa") Long idMesa) {

	    LocalDate fechaReserva = LocalDate.parse(fecha);

	    List<ReservaEntity> reservasDelDia = reservaService.listarPorFechaYPorMesa(idMesa, fechaReserva);

	    List<String> horariosOcupados = reservasDelDia.stream()
	        .map(reserva -> reserva.getHora().toString())
	        .toList();

	    List<String> horariosDisponibles = TRAMOS_HORARIOS.stream()
	        .filter(horario -> !horariosOcupados.contains(horario))
	        .toList();

	    return ResponseEntity.ok(horariosDisponibles);
	}
}