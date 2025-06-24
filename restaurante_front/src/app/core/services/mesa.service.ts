import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mesa } from '../../models/mesa-model';


/**
 * Servicio encargado de gestionar operaciones relacionadas con las mesas del restaurante.
 * Incluye métodos para listar, crear, eliminar y cambiar el estado de las mesas.
 */
@Injectable({
  providedIn: 'root'
})
export class MesaService {
  private apiUrl = 'http://localhost:8080/api/mesas';

  constructor(private http: HttpClient) { }


  /**
     * Obtiene todas las mesas registradas.
     *
     * @returns Observable con una lista de objetos {@link Mesa}
     */
  listarMesas(): Observable<Mesa[]> {
    return this.http.get<Mesa[]>(this.apiUrl);
  }

  /**
   * Crea una nueva mesa en el sistema.
   *
   * @param nombre nombre de la nueva mesa
   * @returns Observable con la mesa creada
   */
  crearMesa(nombre: string): Observable<Mesa> {
    return this.http.post<Mesa>(this.apiUrl, { nombre });
  }

  /**
 * Elimina una mesa a partir de su ID.
 *
 * @param id identificador de la mesa
 * @returns Observable vacío cuando la eliminación es exitosa
 */
  eliminarMesa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cambia el estado de la mesa (activa ↔ inactiva).
   *
   * @param id identificador de la mesa
   * @returns Observable vacío cuando se completa la operación
   */
  cambiarEstadoMesa(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/estado/${id}`, {});
  }
}
