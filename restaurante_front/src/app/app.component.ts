import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './core/services/auth.service';// Asegúrate de que el path sea correcto
import { trigger, transition, style, animate } from '@angular/animations';
import Swal from 'sweetalert2';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    trigger('fadeAnimation', [
      transition('* <=> *', [
        style({ opacity: 0 }),
        animate('500ms', style({ opacity: 1 }))
      ])
    ])
  ]
})
export class AppComponent implements OnInit {

  mostrarNavbar = true;
  private ultimaPosicionScroll = 0;
  nombreUsuario: string | null = null;
  private subscription: Subscription = new Subscription();

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.subscription = this.authService.nombreUsuario$.subscribe(nombre => {
      this.nombreUsuario = nombre;
      this.ultimaPosicionScroll = window.pageYOffset;
    });
  }
  // Método para cerrar sesión
  logout() {
    this.authService.logout();
    // Mostrar mensaje de éxito
    Swal.fire({
      icon: 'success',
      title: 'Sesión cerrada',
      text: 'Has cerrado sesión correctamente',
      timer: 2000,
      showConfirmButton: false
    });
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe(); // muy importante para evitar fugas de memoria
  }

  // Método para pasar el estado de animación del router-outlet
  getRouterOutletState(outlet: any) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
  }

  // Detecta clics globales en el documento
  @HostListener('document:click', ['$event'])
  handleClick(event: Event) {
    const navMenu = document.getElementById('navbarContent');
    const toggle = document.querySelector('.navbar-toggler');

    if (navMenu && toggle && navMenu.classList.contains('show')) {
      const target = event.target as HTMLElement;
      const clickedInside = navMenu.contains(target) || toggle.contains(target);

      if (!clickedInside) {
        toggle.dispatchEvent(new Event('click')); // simula el cierre
      }
    }
  }

  closeNavbar() {
    try {
      const navbar = document.getElementById('navbarContent');
      const toggler = document.querySelector<HTMLElement>('.navbar-toggler');

      if (navbar && toggler && navbar.classList.contains('show')) {
        toggler.dispatchEvent(new Event('click'));
      }
    } catch (error) {
      console.error('Error al cerrar la navbar:', error);
    }
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const posicionActual = window.pageYOffset;

    if (posicionActual < this.ultimaPosicionScroll) {
      // Scroll hacia arriba
      this.mostrarNavbar = true;
    } else {
      // Scroll hacia abajo
      this.mostrarNavbar = false;
    }

    this.ultimaPosicionScroll = posicionActual;
  }


  esAdminRoute(): boolean {
    return this.router.url.startsWith('/admin');
  }

}
