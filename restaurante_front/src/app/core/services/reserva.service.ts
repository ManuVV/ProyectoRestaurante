import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reserva } from '../../models/reservas-model';

/**
 * Servicio encargado de gestionar las reservas del restaurante.
 * Contempla operaciones tanto para usuarios normales como para administradores.
 */
@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) { }

  /**
   * Crea una nueva reserva. El usuario debe estar autenticado.
   *
   * @param reservaData objeto con los datos de la reserva
   * @returns Observable con la reserva creada
   */
  crearReserva(reservaData: any): Observable<any> {
    return this.http.post(this.apiUrl, reservaData);
  }

  /**
   * Devuelve la lista de todas las reservas. Solo accesible por administradores.
   *
   * @returns Observable con una lista de objetos {@link Reserva}
   */
  listarReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(this.apiUrl);
  }


  /**
   * Elimina una reserva como administrador, sin validación de usuario.
   *
   * @param id identificador de la reserva
   * @returns Observable vacío si la eliminación fue exitosa
   */
  eliminarReservaComoAdmin(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

   /**
   * Obtiene las reservas hechas por el usuario autenticado.
   *
   * @returns Observable con las reservas del usuario actual
   */
  listarMisReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.apiUrl}/mis-reservas`);
  }

  /**
   * Cancela una reserva del usuario autenticado.
   *
   * @param id identificador de la reserva
   * @returns Observable vacío si la cancelación fue exitosa
   */
  cancelarMiReserva(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/mis-reservas/${id}`);
  }

  /**
   * Consulta los horarios disponibles para una fecha específica.
   *
   * @param fecha fecha en formato yyyy-MM-dd
   * @returns Observable con una lista de horas disponibles (ej. ["13:00", "14:30"])
   */
  // obtenerHorariosDisponibles(fecha: string): Observable<string[]> {
  //   return this.http.get<string[]>(`${this.apiUrl}/horarios-disponibles?fecha=${fecha}`);
  // }

  obtenerHorariosDisponibles(fecha: string, idMesa: number): Observable<string[]> {
  return this.http.get<string[]>(`${this.apiUrl}/horarios-disponibles?fecha=${fecha}&idMesa=${idMesa}`);
}

}
