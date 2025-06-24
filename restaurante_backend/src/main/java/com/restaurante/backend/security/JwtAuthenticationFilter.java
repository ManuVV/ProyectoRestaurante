package com.restaurante.backend.security;

import com.restaurante.backend.model.UsuarioEntity;
import com.restaurante.backend.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Filtro de autenticación JWT que se ejecuta una vez por petición. Se encarga
 * de validar el token JWT y establecer la autenticación en el contexto de
 * seguridad de Spring.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Método que intercepta cada petición entrante y valida el token JWT si está
	 * presente.
	 *
	 * @param request     petición HTTP
	 * @param response    respuesta HTTP
	 * @param filterChain cadena de filtros
	 * @throws ServletException si ocurre un error de servlet
	 * @throws IOException      si ocurre un error de entrada/salida
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		// Si no hay header o no comienza con "Bearer ", se continúa sin autenticar
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extrae el token y luego el email y rol
		String token = authHeader.substring(7);
		String email = jwtUtils.extractEmail(token);
		String rol = jwtUtils.extractRol(token);

		// Solo continúa si no hay ya autenticación establecida y se ha extraído el
		// email
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByEmail(email);

			// Verifica que el usuario existe y el token es válido
			if (usuarioOpt.isPresent() && jwtUtils.isTokenValid(token)) {
				UsuarioEntity usuario = usuarioOpt.get();

				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol) // muy importante
																										// que tenga el
																										// prefijo ROLE_
				);

				// Crea el token de autenticación y lo asocia al contexto de Spring Security
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario, null,
						authorities);

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
