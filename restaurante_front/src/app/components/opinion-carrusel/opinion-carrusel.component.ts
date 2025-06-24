import { Component } from '@angular/core';

@Component({
  selector: 'app-opinion-carrusel',
  templateUrl: './opinion-carrusel.component.html',
  styleUrl: './opinion-carrusel.component.scss'
})
export class OpinionCarruselComponent {
  opiniones = [
    {
      texto: "¡Una experiencia culinaria increíble, el personal muy atento!",
      autor: "María G."
    },
    {
      texto: "La mejor paella que he probado en años. ¡Volveremos seguro!",
      autor: "Carlos P."
    },
    {
      texto: "Ambiente acogedor y precios justos. Totalmente recomendado.",
      autor: "Lucía M."
    }
  ];
}

