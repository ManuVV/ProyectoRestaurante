import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss']
})
export class RegistroComponent {
  nombre: string = '';
  apellidos: string = '';
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Método que se ejecuta al hacer clic en el botón "Registrarse".
   * Valida que todos los campos estén completos y realiza la llamada al servicio de autenticación.
   */
  registrarse() {
    if (!this.nombre || !this.apellidos || !this.email || !this.password) {
      Swal.fire({
        icon: 'warning',
        title: 'Campos incompletos',
        text: 'Por favor, completa todos los campos.'
      });
      return;
    }

    // Llama al servicio de autenticación para registrar un nuevo usuario
    this.authService.registrar(this.nombre, this.apellidos, this.email, this.password).subscribe({

      // En caso de éxito, muestra alerta y redirige a la página de login
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Registro exitoso',
          text: 'Tu cuenta ha sido creada correctamente',
          timer: 2000,
          showConfirmButton: false
        }).then(() => {
          this.router.navigate(['/login']);
        });
      },

      // En caso de error, gestiona distintos tipos de fallos
      error: (error) => {
        console.error('Error en registro:', error);

        if (error.status === 0) {
          Swal.fire({
            icon: 'error',
            title: 'Error de conexión',
            text: 'No se pudo conectar al servidor. Inténtalo más tarde.'
          });
        } else if (error.status === 400 && error.error?.error) {
          Swal.fire({
            icon: 'error',
            title: 'Error de registro',
            text: error.error.error
          });
        } else if (error.status === 401) {
          Swal.fire({
            icon: 'error',
            title: 'No autorizado',
            text: error.error?.error || 'No tienes permisos para realizar esta acción.'
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error inesperado',
            text: 'Ocurrió un error inesperado. Inténtalo de nuevo más tarde.'
          });
        }
      }
    });
  }

}
