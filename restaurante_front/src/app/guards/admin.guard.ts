import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../core/services/auth.service';

/**
 * Guard que restringe el acceso a rutas reservadas exclusivamente para usuarios con rol ADMIN.
 * Muestra un mensaje de error si el usuario no tiene permisos.
 */
@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

    /**
   * Verifica si el usuario está autenticado y tiene rol ADMIN.
   * Si no cumple con los requisitos, redirige a la página principal y muestra una alerta.
   *
   * @returns `true` si el usuario es administrador, `false` en caso contrario
   */
  async canActivate(): Promise<boolean> {
    if (this.authService.estaLogueado() && this.authService.esAdmin()) {
      console.log('AdminGuard: acceso CONCEDIDO');
      return true;
    }

    console.log('AdminGuard: acceso DENEGADO');

    await Swal.fire({
      icon: 'error',
      title: 'Acceso denegado',
      text: 'No tienes permiso para acceder al panel de administración.',
      confirmButtonColor: '#c21807'
    });

    this.router.navigateByUrl('/');
    return false;
  }

}
