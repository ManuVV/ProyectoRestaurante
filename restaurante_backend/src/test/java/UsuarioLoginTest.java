import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurante.backend.model.RolEntity;
import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.repository.RolRepository;
import com.restaurante.backend.repository.UsuarioRepository;
import com.restaurante.backend.security.JwtUtils;
import com.restaurante.backend.service.UsuarioServiceImpl;
import com.restaurante.backend.vo.LoginRequestVO;
import com.restaurante.backend.vo.LoginResponseVO;
import com.restaurante.backend.vo.UsuarioRequestVO;
import com.restaurante.backend.vo.UsuarioResponseVO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioLoginTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UsuarioRequestVO usuarioRequestVO;
    private UsuarioEntity usuarioEntity;
    private RolEntity rolUser;

    @BeforeEach
    void setUp() {
        usuarioRequestVO = new UsuarioRequestVO();
        usuarioRequestVO.setNombre("Juan");
        usuarioRequestVO.setApellidos("Pérez");
        usuarioRequestVO.setEmail("juan@example.com");
        usuarioRequestVO.setPassword("123456");

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNombre("Juan");
        usuarioEntity.setApellidos("Pérez");
        usuarioEntity.setEmail("juan@example.com");
        usuarioEntity.setPassword("encodedPassword");
        usuarioEntity.setActivo(true);

        rolUser = new RolEntity();       
        rolUser.setNombre("USER");

        usuarioEntity.setRol(rolUser);
    }

    @Test
    void testRegistrarUsuarioExitosamente() {
        // Mock del rol
        when(rolRepository.findByNombre("USER")).thenReturn(Optional.of(rolUser));

        // Mock del password encoder
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");

        // Mock save
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        UsuarioResponseVO response = usuarioService.registrarUsuario(usuarioRequestVO);

        assertNotNull(response);
        assertEquals("Juan", response.getNombre());
        assertEquals("Pérez", response.getApellidos());
        assertEquals("juan@example.com", response.getEmail());
        assertEquals("USER", response.getRol());
        assertTrue(response.isActivo());
    }

    @Test
    void testLoginExitoso() {
        LoginRequestVO loginRequest = new LoginRequestVO();
        loginRequest.setEmail("juan@example.com");
        loginRequest.setPassword("123456");

        // Mock búsqueda de usuario
        when(usuarioRepository.findByEmail("juan@example.com")).thenReturn(Optional.of(usuarioEntity));

        // Mock password encoder: la contraseña coincide
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);

        // Mock token JWT
        when(jwtUtils.generateToken(any(), any(), any())).thenReturn("fake-jwt-token");

        LoginResponseVO response = usuarioService.login(loginRequest);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals("Juan Pérez", response.getNombreCompleto());
        assertEquals("juan@example.com", response.getEmail());
        assertEquals("USER", response.getRol());
    }
}
