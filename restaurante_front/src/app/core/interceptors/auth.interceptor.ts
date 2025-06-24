import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * Interceptor que añade el token JWT a las cabeceras Authorization
 * de todas las peticiones excepto en las rutas públicas (login, registro, etc.).
 */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

   /**
   * Intercepta todas las peticiones HTTP salientes.
   * Si el token JWT está presente y la URL no es pública, lo añade a la cabecera.
   *
   * @param req petición original
   * @param next manejador HTTP
   * @returns petición modificada o la original si no aplica token
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.obtenerToken();

    // Excluir rutas públicas como login, registro y horarios disponibles
    const isPublicRequest = req.url.includes('/api/usuarios/login') ||
      req.url.includes('/api/usuarios/registro') ||
      req.url.includes('/api/usuarios/registro-admin') ||
      req.url.includes('/api/reservas/horarios-disponibles')||
    req.url.includes('/api/platos/carta');


    if (token && !isPublicRequest) {
      const reqClonada = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(reqClonada);
    } else {
      return next.handle(req);
    }
  }
}
