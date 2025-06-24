package com.restaurante.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.time.format.DateTimeFormatter;

import com.restaurante.backend.model.ReservaEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio responsable del envío de correos electrónicos relacionados con
 * reservas. Utiliza {@link JavaMailSender} para enviar mensajes con contenido
 * HTML y recursos embebidos.
 */
@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Envía un correo de confirmación al usuario tras crear una reserva. El mensaje
	 * contiene un diseño HTML con los detalles de la reserva. Se ejecuta de forma
	 * asincrónica para no bloquear la petición HTTP.
	 *
	 * @param reserva la reserva recién creada
	 */
	@Async
	public void enviarConfirmacionReserva(ReservaEntity reserva) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(reserva.getUsuario().getEmail());
			helper.setSubject("Confirmación de reserva");

			DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

			String fechaFormateada = reserva.getFecha().format(fechaFormatter);
			String horaFormateada = reserva.getHora().format(horaFormatter);

			String contenidoHtml = """
					<html>
					<body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
					    <div style="max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
					        <img src='cid:logoRestaurante' style='width: 200px; display: block; margin: 0 auto 20px auto;' />
					        <h2 style="color: #333333;">Hola %s,</h2>
					        <p style="font-size: 16px; color: #555555;">Tu reserva se ha registrado correctamente:</p>
					        <table style="width: 100%%; font-size: 16px; color: #333;">
					            <tr><td><strong>Nombre:</strong></td><td>%s</td></tr>
					            <tr><td><strong>Fecha:</strong></td><td>%s</td></tr>
					            <tr><td><strong>Hora:</strong></td><td>%s</td></tr>
					            <tr><td><strong>Personas:</strong></td><td>%d</td></tr>
					            <tr><td><strong>Mesa:</strong></td><td>%s</td></tr>
					        </table>
					        <p style="font-size: 16px; color: #555555;">Gracias por reservar con nosotros.</p>
					        <p style="font-weight: bold; font-size: 16px; color: #8B4513;">Restaurante La Comarca</p>
					    </div>
					</body>
					</html>
					"""
					.formatted(reserva.getUsuario().getNombre(), reserva.getNombre(), fechaFormateada, horaFormateada,
							reserva.getNumeroPersonas(), reserva.getMesa().getNombre());

			helper.setText(contenidoHtml, true);

			// Adjuntar imagen como recurso embebido
			FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/logo.png"));
			helper.addInline("logoRestaurante", logo);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envía un correo al usuario cuando cancela una reserva. El mensaje contiene un
	 * diseño HTML con los detalles de la reserva cancelada. Se ejecuta de forma
	 * asincrónica.
	 *
	 * @param reserva la reserva que ha sido cancelada
	 */
	@Async
	public void enviarCancelacionReserva(ReservaEntity reserva) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(reserva.getUsuario().getEmail());
			helper.setSubject("Cancelación de reserva");

			DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

			String fechaFormateada = reserva.getFecha().format(fechaFormatter);
			String horaFormateada = reserva.getHora().format(horaFormatter);

			String contenidoHtml = """
					<html>
					<body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
					    <div style="max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
					        <img src='cid:logoRestaurante' style='width: 200px; display: block; margin: 0 auto 20px auto;' />
					        <h2 style="color: #8B0000;">Hola %s,</h2>
					        <p style="font-size: 16px; color: #555555;">Tu reserva ha sido <strong>cancelada</strong>.</p>
					        <p style="font-size: 16px; color: #555555;">Detalles de la reserva cancelada:</p>
					        <table style="width: 100%%; font-size: 16px; color: #333;">
					            <tr><td><strong>Nombre:</strong></td><td>%s</td></tr>
					            <tr><td><strong>Fecha:</strong></td><td>%s</td></tr>
					            <tr><td><strong>Hora:</strong></td><td>%s</td></tr>
					            <tr><td><strong>Personas:</strong></td><td>%d</td></tr>
					            <tr><td><strong>Mesa:</strong></td><td>%s</td></tr>
					        </table>
					        <p style="font-size: 16px; color: #555555;">Si tienes alguna duda, puedes contactarnos en inforeslacomarca@gmail.com.</p>
					        <p style="font-weight: bold; font-size: 16px; color: #8B4513;">Restaurante La Comarca</p>
					    </div>
					</body>
					</html>
					"""
					.formatted(reserva.getUsuario().getNombre(), reserva.getNombre(), fechaFormateada, horaFormateada,
							reserva.getNumeroPersonas(), reserva.getMesa().getNombre());

			helper.setText(contenidoHtml, true);

			FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/logo.png"));
			helper.addInline("logoRestaurante", logo);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
