import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from '../../models/categoría-model';

/**
 * Servicio encargado de comunicarse con el backend para operaciones relacionadas con categorías de platos.
 */
@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private apiUrl = 'http://localhost:8080/api/categorias';

  constructor(private http: HttpClient) {}

    /**
   * Recupera todas las categorías disponibles desde el backend.
   *
   * @returns Observable con una lista de objetos de tipo {@link Categoria}
   */
  listarCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }
}
