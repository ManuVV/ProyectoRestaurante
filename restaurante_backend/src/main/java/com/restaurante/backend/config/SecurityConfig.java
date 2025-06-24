package com.restaurante.backend.config;

import com.restaurante.backend.security.JwtAuthenticationFilter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Clase de configuración de seguridad para la aplicación. Define las políticas
 * de seguridad, autorización y filtros de JWT.
 */
@EnableMethodSecurity // Habilita anotaciones como @PreAuthorize
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Bean para codificar contraseñas usando BCrypt. Se usa en el registro y
	 * autenticación de usuarios.
	 *
	 * @return un codificador BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configura la cadena de filtros de seguridad. Define rutas públicas, rutas
	 * protegidas por roles y autenticación JWT.
	 *
	 * @param http el objeto HttpSecurity proporcionado por Spring
	 * @return la cadena de filtros de seguridad configurada
	 * @throws Exception si ocurre un error en la configuración
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Desactiva CSRF (recomendado para APIs REST)
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS
				.httpBasic(httpBasic -> httpBasic.disable()) // No se usa autenticación básica
				.formLogin(form -> form.disable()).authorizeHttpRequests(auth -> auth
						// Rutas públicas sin necesidad de autenticación
						.requestMatchers("/api/usuarios/registro", "/api/usuarios/login",
								"/api/usuarios/registro-admin", "/api/platos/carta",
								"/api/reservas/horarios-disponibles", "/api/mesas")
						.permitAll()

						// Solo accesible por usuarios con rol ADMIN
						.requestMatchers("/api/admin/**").hasRole("ADMIN")

						// El resto requiere estar autenticado
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Configura el origen y políticas de CORS. Permite que el frontend ( Angular en
	 * localhost:4200) acceda al backend.
	 *
	 * @return fuente de configuración CORS para la aplicación
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Dominio del frontend
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
		configuration.setAllowedHeaders(List.of("*")); // Headers permitidos
		configuration.setAllowCredentials(true); // Permite el uso de autenticación

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
