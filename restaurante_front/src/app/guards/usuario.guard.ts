import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../core/services/auth.service';

/**
 * Guard que permite el acceso solo a usuarios autenticados con rol "USER".
 * Muestra un mensaje de error y redirige a la página principal si no se cumplen los requisitos.
 */
@Injectable({
  providedIn: 'root'
})
export class UsuarioGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Verifica si el usuario está logueado y tiene el rol USER.
   *
   * @returns una promesa que resuelve a `true` si el acceso está permitido, `false` si se redirige.
   */
  canActivate(): Promise<boolean> {
    const tokenExiste = this.authService.estaLogueado();
    const esUsuario = this.authService.obtenerRolesUsuario().includes('USER');

    if (tokenExiste && esUsuario) {
      return Promise.resolve(true);
    }

    return Swal.fire({
      icon: 'error',
      title: 'Acceso denegado',
      text: 'Debes iniciar sesión como usuario para acceder a esta sección.',
      confirmButtonColor: '#c21807'
    }).then(() => {
      this.router.navigateByUrl('/');
      return false;
    });
  }
}
