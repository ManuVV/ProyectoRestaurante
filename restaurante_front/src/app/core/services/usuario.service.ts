import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../../models/usuario-model';

/**
 * Servicio que gestiona operaciones administrativas sobre los usuarios del sistema.
 * Incluye funciones para listar, eliminar y activar/desactivar cuentas.
 */
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  /**
   * Recupera todos los usuarios registrados en el sistema.
   * Solo accesible para administradores.
   *
   * @returns Observable con una lista de objetos {@link Usuario}
   */
  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  /**
   * Elimina un usuario por su identificador.
   * También borra sus reservas asociadas (según lógica backend).
   *
   * @param id identificador del usuario a eliminar
   * @returns Observable vacío si la operación es exitosa
   */
  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cambia el estado del usuario entre activo e inactivo.
   * Esta acción es útil para suspender temporalmente usuarios sin eliminarlos.
   *
   * @param id identificador del usuario
   * @returns Observable vacío si el cambio se realiza correctamente
   */
  cambiarEstadoUsuario(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/estado/${id}`, {});
  }
}
