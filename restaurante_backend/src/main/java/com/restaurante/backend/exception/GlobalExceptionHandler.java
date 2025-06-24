package com.restaurante.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.restaurante.backend.exception.AuthException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación. Permite capturar
 * errores comunes y devolver respuestas claras y estructuradas al cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Maneja errores de validación de campos en peticiones con anotaciones @Valid.
	 * Captura errores de tipo MethodArgumentNotValidException y devuelve un mapa
	 * con los campos y sus mensajes de error.
	 *
	 * @param ex excepción lanzada cuando fallan las validaciones
	 * @return ResponseEntity con errores por campo y código 400 Bad Request
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errores.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja errores de integridad de datos, como intentos de insertar registros
	 * duplicados en campos únicos.
	 *
	 * @param ex excepción de integridad de datos
	 * @return ResponseEntity con mensaje de error genérico y código 400 Bad Request
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Ya existe un registro con ese valor único");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Captura cualquier excepción no manejada previamente y devuelve un error
	 * genérico.
	 *
	 * @param ex excepción genérica
	 * @return ResponseEntity con mensaje de error y código 500 Internal Server
	 *         Error
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Maneja errores relacionados con autenticación personalizada mediante
	 * AuthException.
	 *
	 * @param ex excepción de autenticación
	 * @return ResponseEntity con mensaje de error y código 401 Unauthorized
	 */
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<Map<String, String>> handleAuthException(AuthException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); // 401
	}

	/**
	 * Maneja errores causados por argumentos ilegales en la lógica de negocio.
	 *
	 * @param ex excepción por argumento inválido
	 * @return ResponseEntity con mensaje de error y código 400 Bad Request
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, String> errorBody = new HashMap<>();
		errorBody.put("error", ex.getMessage());
		return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
	}
}
