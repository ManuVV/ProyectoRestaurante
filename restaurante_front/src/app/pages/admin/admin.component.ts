import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {

  constructor(private authService: AuthService, private router: Router) {}



  cerrarSesion() {
    Swal.fire({
      title: '¿Cerrar sesión?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, cerrar sesión',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.logout();
        this.router.navigate(['/']);
      }
    });
  }


  closeNavbar() {
    try {
      const navbar = document.getElementById('adminNavbar');
      const toggler = document.querySelector<HTMLElement>('.navbar-toggler');

      if (navbar && toggler && navbar.classList.contains('show')) {
        toggler.dispatchEvent(new Event('click'));
      }
    } catch (error) {
      console.error('Error al cerrar la navbar de admin:', error);
    }
  }

}
