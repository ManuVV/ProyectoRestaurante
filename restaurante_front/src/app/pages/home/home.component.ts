import { Component } from '@angular/core';

/**
 * Componente principal que representa la página de inicio del restaurante.
 * Muestra un carrusel de imágenes de fondo que se actualiza automáticamente cada 6 segundos.
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  imagenes = [
    '/hero1.jpg',
    '/hero2.jpg',
    '/hero3.jpg'
  ];

  imagenActual = this.imagenes[0];
  indice = 0;

  /**
   * Al iniciar el componente, se establece un intervalo que cambia la imagen cada 6 segundos.
   */
  ngOnInit(): void {
    setInterval(() => {
      this.indice = (this.indice + 1) % this.imagenes.length;
      this.imagenActual = this.imagenes[this.indice];
    }, 6000); // cambia cada 6 segundos
  }

}
