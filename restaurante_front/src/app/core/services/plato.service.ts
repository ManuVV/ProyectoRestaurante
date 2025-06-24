import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plato } from '../../models/plato-model';

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los platos del restaurante.
 * Incluye funcionalidades CRUD y gestión de visibilidad en carta.
 */
@Injectable({
  providedIn: 'root'
})
export class PlatoService {
  private apiUrl = 'http://localhost:8080/api/platos';

  constructor(private http: HttpClient) { }


/**
 * Crea un nuevo plato en el sistema.
 *
 * @param plato objeto parcial que contiene los datos del nuevo plato
 * @returns Observable con el plato creado
 */
crearPlato(plato: Partial<Plato>): Observable<Plato> {
    return this.http.post<Plato>(this.apiUrl, plato);
  }

/**
 * Obtiene todos los platos del sistema, incluyendo los no visibles en carta.
 *
 * @returns Observable con una lista de platos
 */
  listarPlatos(): Observable<Plato[]> {
    return this.http.get<Plato[]>(this.apiUrl);
  }

/**
 * Cambia el estado de visibilidad de un plato (disponible/no disponible en carta).
 *
 * @param id identificador del plato
 * @returns Observable vacío cuando la operación es exitosa
 */
  cambiarEstadoPlato(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/estado/${id}`, {});
  }

/**
 * Obtiene solo los platos marcados como disponibles en carta.
 *
 * @returns Observable con la lista de platos visibles para clientes
 */
  listarPlatosDisponiblesEnCarta(): Observable<Plato[]> {
    return this.http.get<Plato[]>(`${this.apiUrl}/carta`);
  }

/**
 * Actualiza los datos de un plato existente.
 *
 * @param id identificador del plato a modificar
 * @param plato objeto parcial con los nuevos datos
 * @returns Observable con el plato actualizado
 */
  actualizarPlato(id: number, plato: Partial<Plato>): Observable<Plato> {
    return this.http.put<Plato>(`${this.apiUrl}/${id}`, plato);
  }

/**
 * Elimina un plato del sistema por su ID.
 *
 * @param id identificador del plato a eliminar
 * @returns Observable vacío cuando se elimina correctamente
 */
  eliminarPlato(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
