import { Component, OnInit } from '@angular/core';
import { PlatoService } from '../../core/services/plato.service';
import { Plato } from '../../models/plato-model';


/**
 * Componente que representa la carta pública del restaurante.
 * Muestra los platos disponibles agrupados por categoría.
 */
@Component({
  selector: 'app-carta',
  templateUrl: './carta.component.html',
  styleUrls: ['./carta.component.scss']
})
export class CartaComponent implements OnInit {

  ordenCategorias: string[] = ['Entrantes', 'Principales', 'Postres', 'Bebidas'];

  platosAgrupados: { [categoria: string]: Plato[] } = {};

  categoriasOrdenadas: { nombre: string, platos: Plato[] }[] = [];

  categoriaSeleccionada: string | null = null;

  constructor(private platoService: PlatoService) { }

/**
 * Al iniciar el componente, se solicitan los platos disponibles en carta,
 * se agrupan por categoría y se organizan según el orden definido.
 */
  ngOnInit(): void {
    this.platoService.listarPlatosDisponiblesEnCarta().subscribe(platos => {
      console.log('Platos recibidos:', platos)
      const agrupados = this.agruparPorCategoria(platos);

      this.categoriasOrdenadas = this.ordenCategorias
        .filter(nombre => agrupados[nombre]) // solo categorías que existen
        .map(nombre => ({
          nombre,
          platos: agrupados[nombre]
        }));
    });
  }

/**
 * Agrupa un array de platos por su nombre de categoría.
 * Si un plato no tiene categoría, se agrupa bajo 'Sin categoría'.
 *
 * @param platos lista de platos
 * @returns objeto con claves de categoría y sus platos correspondientes
 */
  agruparPorCategoria(platos: Plato[]): { [categoria: string]: Plato[] } {
    return platos.reduce((acc, plato) => {
      const cat = plato.nombreCategoria || 'Sin categoría';
      if (!acc[cat]) acc[cat] = [];
      acc[cat].push(plato);
      return acc;
    }, {} as { [categoria: string]: Plato[] });
  }

/**
 * Indica si se debe mostrar una categoría determinada en la vista.
 * Si no hay ninguna categoría seleccionada, se muestran todas.
 *
 * @param nombreCategoria nombre de la categoría a evaluar
 * @returns `true` si se debe mostrar la categoría
 */
  mostrarCategoria(nombreCategoria: string): boolean {
    return !this.categoriaSeleccionada || this.categoriaSeleccionada === nombreCategoria;
  }
}
