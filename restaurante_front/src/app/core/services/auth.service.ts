import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';

/**
 * Interfaz que representa el contenido del token JWT.
 */
export interface JwtPayload {
  sub: string;
  nombre: string;
  rol: string;

}


/**
 * Servicio de autenticación que maneja login, registro, token JWT
 * y datos del usuario autenticado.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) { }

  /**
   * Subject que almacena el nombre del usuario autenticado.
   * Permite notificar a los componentes cuando cambia el nombre.
   */
  private nombreUsuarioSubject = new BehaviorSubject<string | null>(this.obtenerNombreUsuario());

  /**
  * Observable público para suscribirse a los cambios del nombre del usuario.
  */
  nombreUsuario$ = this.nombreUsuarioSubject.asObservable();

  /**
   * Realiza la llamada al backend para autenticar al usuario.
   *
   * @param email correo electrónico
   * @param password contraseña
   * @returns Observable con el token y datos del usuario
   */
  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password });
  }

  /**
   * Realiza el registro de un nuevo usuario.
   *
   * @param nombre nombre del usuario
   * @param apellidos apellidos del usuario
   * @param email correo electrónico
   * @param password contraseña
   * @returns Observable con la respuesta del backend
   */
  registrar(nombre: string, apellidos: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, { nombre, apellidos, email, password });
  }


  /**
   * Guarda el token JWT en localStorage y actualiza el nombre del usuario.
   *
   * @param token token JWT recibido del backend
   */
  guardarToken(token: string) {
    localStorage.setItem('token', token);
    this.nombreUsuarioSubject.next(this.obtenerNombreUsuario());
  }

  /**
   * Elimina el token de localStorage y resetea el nombre del usuario.
   */
  logout() {
    localStorage.removeItem('token');
    this.nombreUsuarioSubject.next(null);
  }

  /**
   * Obtiene el token almacenado en localStorage.
   *
   * @returns token JWT o null si no existe
   */
  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  /**
   * Indica si el usuario está autenticado.
   *
   * @returns true si hay un token, false si no
   */
  estaLogueado(): boolean {
    return !!this.obtenerToken();
  }

  /**
   * Extrae el nombre del usuario a partir del token JWT.
   *
   * @returns nombre o null si no hay token o no se puede decodificar
   */
  obtenerNombreUsuario(): string | null {
    const token = this.obtenerToken();
    if (!token) {
      return null;
    }

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.nombre || null;
    } catch (error) {
      console.error('Error al decodificar token', error);
      return null;
    }
  }

  /**
   * Extrae el rol del usuario a partir del token JWT.
   *
   * @returns array con el rol del usuario, vacío si no hay token o no se puede decodificar
   */
  obtenerRolesUsuario(): string[] {
    const token = this.obtenerToken();
    if (!token) {
      return [];
    }

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return [decoded.rol];
    } catch (error) {
      console.error('Error al decodificar roles del token', error);
      return [];
    }
  }


  /**
 * Verifica si el usuario autenticado tiene el rol ADMIN.
 *
 * @returns true si es administrador, false si no
 */
  esAdmin(): boolean {
    const roles = this.obtenerRolesUsuario();
    return roles.includes('ADMIN');
  }

}
