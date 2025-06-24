import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../core/services/auth.service';


/**
 * Componente que gestiona el inicio de sesión de usuarios.
 * Valida el formulario, muestra mensajes de error y redirige tras login exitoso.
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  error: string | null = null;
  cargando: boolean = false;
  emailError: string | null = null;
  passwordError: string | null = null;


  constructor(private authService: AuthService, private router: Router) { }


  /**
   * Inicia el proceso de autenticación.
   * Valida los campos del formulario, muestra errores con SweetAlert2 y redirige según el rol.
   */
  login() {
    this.emailError = null;
    this.passwordError = null;
    this.error = null;
    this.cargando = true;

    // Validar campos vacíos
    if (!this.email || !this.password) {
      this.error = 'Debes completar todos los campos.';
      this.cargando = false;
      return;
    }

    // Validar formato del email
    const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;
    if (!emailPattern.test(this.email)) {
      this.error = 'Introduce un correo electrónico válido.';
      this.cargando = false;
      return;
    }

    // // Validar contraseña: mínimo 6 caracteres y al menos un número
    // const passwordPattern = /^(?=.*\d).{6,}$/;
    // if (!passwordPattern.test(this.password)) {
    //   this.error = 'La contraseña debe tener al menos 6 caracteres e incluir al menos un número.';
    //   this.cargando = false;
    //   return;
    // }

    // Si todo está correcto → continúa con el login
    this.authService.login(this.email, this.password).subscribe({
      next: (respuesta) => {
        this.authService.guardarToken(respuesta.token);

        const esAdmin = this.authService.esAdmin();

        Swal.fire({
          icon: 'success',
          title: esAdmin ? 'Bienvenido Administrador' : 'Bienvenido',
          text: esAdmin ? 'Accediendo al panel de administración...' : 'Has iniciado sesión correctamente.',
          timer: 1600,
          showConfirmButton: false
        });

        setTimeout(() => {
          if (esAdmin) {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/']);
          }
        }, 1700);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error en login:', error);
        this.cargando = false;
        if (error.status === 0) {
          Swal.fire({
            icon: 'error',
            title: 'Error de conexión',
            text: 'No se pudo conectar al servidor. Inténtalo más tarde.'
          });
        } else if (error.status === 401) {
          Swal.fire({
            icon: 'error',
            title: 'Error de autenticación',
            text: error.error?.error || 'Email o contraseña incorrectos.'
          });
        } else if (error.status === 403) {
          Swal.fire({
            icon: 'error',
            title: 'Acceso denegado',
            text: 'Tu cuenta está deshabilitada.'
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error inesperado',
            text: 'Ocurrió un error inesperado. Inténtalo más tarde.'
          });
        }
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }


  // login() {
  //   this.emailError = null;
  //   this.passwordError = null;
  //   this.error = null;
  //   this.cargando = true;

  //   if (!this.email || !this.password) {
  //     this.error = 'Debes completar todos los campos.';
  //     this.cargando = false;
  //     return;
  //   }

  //   this.authService.login(this.email, this.password).subscribe({
  //     next: (respuesta) => {
  //       this.authService.guardarToken(respuesta.token);

  //       const esAdmin = this.authService.esAdmin();

  //       // Mostrar mensaje personalizado
  //       Swal.fire({
  //         icon: 'success',
  //         title: esAdmin ? 'Bienvenido Administrador' : 'Bienvenido',
  //         text: esAdmin ? 'Accediendo al panel de administración...' : 'Has iniciado sesión correctamente.',
  //         timer: 1600,
  //         showConfirmButton: false
  //       });

  //       // Redirige después del mensaje
  //       setTimeout(() => {
  //         if (esAdmin) {
  //           this.router.navigate(['/admin']);
  //         } else {
  //           this.router.navigate(['/']);
  //         }
  //       }, 1700);
  //     },
  //     error: (error: HttpErrorResponse) => {
  //       console.error('Error en login:', error);
  //       this.cargando = false; // muy importante
  //       if (error.status === 0) {
  //         Swal.fire({
  //           icon: 'error',
  //           title: 'Error de conexión',
  //           text: 'No se pudo conectar al servidor. Inténtalo más tarde.'
  //         });
  //       } else if (error.status === 401) {
  //         Swal.fire({
  //           icon: 'error',
  //           title: 'Error de autenticación',
  //           text: error.error?.error || 'Email o contraseña incorrectos.'
  //         });
  //       } else if (error.status === 403) {
  //         Swal.fire({
  //           icon: 'error',
  //           title: 'Acceso denegado',
  //           text: 'Tu cuenta está deshabilitada.'
  //         });

  //       } else {
  //         Swal.fire({
  //           icon: 'error',
  //           title: 'Error inesperado',
  //           text: 'Ocurrió un error inesperado. Inténtalo más tarde.'
  //         });
  //       }
  //     },
  //     complete: () => {
  //       this.cargando = false;
  //     }
  //   });
  // }
}
