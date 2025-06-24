import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurante.backend.model.MesaEntity;
import com.restaurante.backend.model.ReservaEntity;
import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.repository.MesaRepository;
import com.restaurante.backend.repository.ReservaRepository;
import com.restaurante.backend.service.EmailService;
import com.restaurante.backend.service.ReservaServiceImpl;
import com.restaurante.backend.vo.ReservaRequestVO;

@ExtendWith(MockitoExtension.class)
class ReservasServiceTest {

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EmailService emailService;

    private ReservaRequestVO requestVO;
    private MesaEntity mesa;
    private UsuarioEntity usuario;

    @BeforeEach
    void setUp() {
        requestVO = new ReservaRequestVO();
        requestVO.setIdMesa(1L);
        requestVO.setNombre("Juan");
        requestVO.setFecha(java.time.LocalDate.now());
        requestVO.setHora(java.time.LocalTime.of(20, 0));
        requestVO.setNumeroPersonas(4);

        mesa = new MesaEntity();
        mesa.setId(1L);

        usuario = new UsuarioEntity();
        usuario.setId(1L);
    }

    @Test
    void testCrearReservaExitosamente() {
        // Simulamos que la mesa existe
        when(mesaRepository.findById(1L)).thenReturn(java.util.Optional.of(mesa));

        // Simulamos que la mesa no está ocupada en esa fecha/hora
        when(reservaRepository.existsByMesaIdAndFechaAndHora(1L, requestVO.getFecha(), requestVO.getHora()))
                .thenReturn(false);

        // Simulamos que la reserva se guarda correctamente
        ReservaEntity reservaGuardada = new ReservaEntity();
        reservaGuardada.setId(100L);
        reservaGuardada.setNombre("Juan");
        reservaGuardada.setFecha(requestVO.getFecha());
        reservaGuardada.setHora(requestVO.getHora());
        reservaGuardada.setNumeroPersonas(4);
        reservaGuardada.setMesa(mesa);
        reservaGuardada.setUsuario(usuario);

        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reservaGuardada);

        // Ejecutamos la lógica real
        var response = reservaService.crearReserva(requestVO, usuario);

        // Verificamos resultados
        assertNotNull(response);
        assertEquals(100L, response.getId());
        verify(emailService).enviarConfirmacionReserva(any(ReservaEntity.class));
    }
    
    @Test
    void testCrearReservaExcesoDePersonas() {
        // Preparamos una reserva con 9 personas (>8)
        requestVO.setNumeroPersonas(9);

        when(mesaRepository.findById(1L)).thenReturn(java.util.Optional.of(mesa));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaService.crearReserva(requestVO, usuario);
        });

        assertEquals("No se permiten más de 8 personas por reserva.", exception.getMessage());
    }
    
}
