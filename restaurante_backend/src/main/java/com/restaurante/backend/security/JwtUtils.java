package com.restaurante.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilidad para la generación y validación de tokens JWT en la aplicación.
 * Encapsula la lógica de construcción, firma, parsing y extracción de
 * información del token.
 */
@Component
public class JwtUtils {

	/**
	 * Clave secreta utilizada para firmar y verificar los tokens JWT. Generada con
	 * el algoritmo HS256.
	 */
	private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); //

	/**
	 * Tiempo de expiración del token: 24 horas (en milisegundos).
	 */
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24h

	/**
	 * Genera un token JWT con el email como sujeto, y con claims adicionales como
	 * nombre y rol.
	 *
	 * @param email  correo electrónico del usuario
	 * @param nombre nombre del usuario
	 * @param rol    rol del usuario ( "ADMIN" o "USER")
	 * @return token JWT generado como cadena
	 */
	public String generateToken(String email, String nombre, String rol) {
		return Jwts.builder().setSubject(email).claim("nombre", nombre).claim("rol", rol).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SECRET_KEY).compact();
	}

	/**
	 * Extrae el email (subject) del token JWT.
	 *
	 * @param token token JWT del cual se extrae el email
	 * @return email del usuario
	 */
	public String extractEmail(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * Verifica si un token JWT es válido y no ha expirado.
	 *
	 * @param token token JWT a validar
	 * @return true si el token es válido, false si está expirado o es inválido
	 */
	public boolean isTokenValid(String token) {
		try {
			Claims claims = getClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Extrae el rol del usuario desde los claims del token.
	 *
	 * @param token token JWT
	 * @return rol del usuario (por ejemplo, "ADMIN")
	 */
	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

	/**
	 * Extrae todos los claims contenidos en el token.
	 *
	 * @param token token JWT
	 * @return objeto Claims que contiene todos los datos del token
	 */
	public String extractRol(String token) {
		return getClaims(token).get("rol", String.class);
	}

}
