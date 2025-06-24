package com.restaurante.backend.service;

import com.restaurante.backend.model.MesaEntity;
import com.restaurante.backend.model.ReservaEntity;
import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.repository.ReservaRepository;
import com.restaurante.backend.vo.ReservaRequestVO;
import com.restaurante.backend.vo.ReservaResponseVO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio {@link ReservaService} que gestiona
 * la lógica de negocio relacionada con las reservas del restaurante.
 */
@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private MesaRepository mesaRepository;
    
    @Autowired
    private EmailService emailService;

    /**
     * Crea una nueva reserva validando que:
     * - La mesa exista.
     * - No se supere el límite de 8 personas.
     * - No esté ocupada la mesa para la fecha y hora seleccionadas.
     * - También envía un correo de confirmación.
     *
     * @param vo objeto con los datos de la reserva
     * @param usuario usuario que realiza la reserva
     * @return objeto de respuesta con los detalles de la reserva creada
     */
    @Override
    public ReservaResponseVO crearReserva(ReservaRequestVO vo, UsuarioEntity usuario) {
        MesaEntity mesa = mesaRepository.findById(vo.getIdMesa())
            .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        if (vo.getNumeroPersonas() > 8) {
            throw new IllegalArgumentException("No se permiten más de 8 personas por reserva.");
        }

        boolean ocupada = reservaRepository.existsByMesaIdAndFechaAndHora(
                mesa.getId(), vo.getFecha(), vo.getHora());

        if (ocupada) {
            throw new IllegalStateException("La mesa ya está reservada para esa fecha y hora.");
        }

        ReservaEntity reserva = new ReservaEntity();
        reserva.setNombre(vo.getNombre());
        reserva.setFecha(vo.getFecha());
        reserva.setHora(vo.getHora());
        reserva.setNumeroPersonas(vo.getNumeroPersonas());
        reserva.setMesa(mesa);
        reserva.setUsuario(usuario);

        ReservaEntity creada = reservaRepository.save(reserva);
        
        //Enviamos correo
        try {
            emailService.enviarConfirmacionReserva(creada);
        } catch (Exception e) {
          
            System.err.println("No se pudo enviar el correo: " + e.getMessage());
        }
        
        return toVO(creada);
    }
    
    /**
     * Devuelve todas las reservas del sistema.
     *
     * @return lista de reservas en formato de respuesta
     */
    @Override
    public List<ReservaResponseVO> listarReservas() {
        List<ReservaEntity> reservas = reservaRepository.findAll();
        return reservas.stream()
                .map(this::toVO)
                .toList();
    }
    
    
    
    /**
     * Devuelve las reservas realizadas por un usuario específico.
     *
     * @param usuario usuario autenticado
     * @return lista de reservas asociadas al usuario
     */
    @Override
    public List<ReservaResponseVO> listarPorUsuario(UsuarioEntity usuario) {
        List<ReservaEntity> reservas = reservaRepository.findByUsuario(usuario);
        return reservas.stream()
                .map(this::toVO)
                .toList();
    }
    
    
    /**
     * Devuelve las reservas realizadas para una fecha concreta.
     *
     * @param fecha fecha en la que buscar reservas
     * @return lista de entidades de reserva
     */
	/*
	 * @Override public List<ReservaEntity> listarPorFecha(LocalDate fecha) { return
	 * reservaRepository.findByFecha(fecha); }
	 */
    
    /**
     * Elimina una reserva como administrador.
     * Solo se requiere el ID.
     *
     * @param id identificador de la reserva
     * @throws RuntimeException si la reserva no existe
     */
    @Override
    @Transactional
    public void eliminarReservaPorAdmin(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada."));
        reservaRepository.delete(reserva);
    }
    
    /**
     * Permite a un usuario cancelar su propia reserva.
     * Verifica que la reserva le pertenezca y luego la elimina.
     * También envía un correo de cancelación.
     *
     * @param id identificador de la reserva
     * @param usuario usuario autenticado
     * @throws RuntimeException si la reserva no existe o no pertenece al usuario
     */
    @Override
    @Transactional
    public void cancelarReserva(Long id, UsuarioEntity usuario) {
        ReservaEntity reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada."));

        if (!reserva.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No puedes cancelar una reserva que no es tuya.");
        }

        reservaRepository.delete(reserva);
        
        // Enviar correo de cancelación
        emailService.enviarCancelacionReserva(reserva);
    }
    
    /**
     * Devuelve las reservas realizadas en una fecha concreta para una mesa concreta.
     *
     * @param idMesa ID de la mesa
     * @param fecha  fecha de la reserva
     * @return lista de reservas coincidentes
     */
    @Override
    public List<ReservaEntity> listarPorFechaYPorMesa(Long idMesa, LocalDate fecha) {
        return reservaRepository.findByMesaIdAndFecha(idMesa, fecha);
    }
    
    
    
    /**
     * Convierte una entidad {@link ReservaEntity} a un objeto de respuesta {@link ReservaResponseVO}.
     *
     * @param entity entidad reserva
     * @return objeto VO con información para el frontend
     */
    private ReservaResponseVO toVO(ReservaEntity entity) {
        ReservaResponseVO vo = new ReservaResponseVO();
        vo.setId(entity.getId());
        vo.setNombre(entity.getNombre());
        vo.setFecha(entity.getFecha());
        vo.setHora(entity.getHora());
        vo.setNumeroPersonas(entity.getNumeroPersonas());

        if (entity.getMesa() != null) {
            vo.setIdMesa(entity.getMesa().getId());
            vo.setNombreMesa(entity.getMesa().getNombre());
        }

        if (entity.getUsuario() != null) {
            vo.setNombreUsuario(entity.getUsuario().getNombre() + " " + entity.getUsuario().getApellidos());
            vo.setEmailUsuario(entity.getUsuario().getEmail()); 
        }

        return vo;
    }
    
    
}
