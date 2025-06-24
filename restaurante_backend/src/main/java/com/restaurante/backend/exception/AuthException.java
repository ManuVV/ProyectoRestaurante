package com.restaurante.backend.exception;

/**
 * Excepción personalizada utilizada para errores relacionados con la
 * autenticación.
 * 
 * Se lanza, por ejemplo, cuando las credenciales son inválidas o el usuario no
 * está autorizado. Esta excepción puede ser capturada y manejada globalmente
 * para devolver mensajes claros al frontend.
 */
public class AuthException extends RuntimeException {

	/**
	 * Constructor que recibe un mensaje descriptivo del error de autenticación.
	 *
	 * @param message mensaje que describe el motivo del error
	 */
	public AuthException(String message) {
		super(message);
	}

}
