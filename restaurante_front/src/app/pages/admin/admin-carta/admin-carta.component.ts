import { PlatoService } from '../../../core/services/plato.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Plato } from '../../../models/plato-model';
import { Categoria } from '../../../models/categoría-model';
import { CategoriaService } from '../../../core/services/categoria.service';

@Component({
  selector: 'app-admin-carta',
  templateUrl: './admin-carta.component.html',
  styleUrl: './admin-carta.component.scss'
})
export class AdminCartaComponent {

  platos: Plato[] = [];
  categorias: Categoria[] = [];


  constructor(private platoService: PlatoService,  private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.cargarPlatos();
    this.categoriaService.listarCategorias().subscribe((data) => {
      this.categorias = data;
    });
  }

  cargarPlatos(): void {
    this.platoService.listarPlatos().subscribe({
      next: (data) => {
        this.platos = data;
      },
      error: (err) => {
        console.error('Error al cargar platos:', err);
      }
    });
  }

  cambiarEstadoPlato(id: number): void {
    this.platoService.cambiarEstadoPlato(id).subscribe({
      next: () => {
        Swal.fire('Éxito', 'El estado del plato ha sido actualizado.', 'success');
        this.cargarPlatos(); // recargar después de cambiar
      },
      error: (err) => {
        Swal.fire('Error', 'No se pudo cambiar el estado del plato.', 'error');
      }
    });
  }

  abrirModalEditar(plato: Plato): void {
    Swal.fire({
      title: 'Editar plato',
      html:
        `<input id="nombre" class="swal2-input" placeholder="Nombre" value="${plato.nombre}">
         <input id="descripcion" class="swal2-input" placeholder="Descripción" value="${plato.descripcion}">
         <input id="precio" type="number" class="swal2-input" placeholder="Precio" value="${plato.precio}">
         <input id="imagen" class="swal2-input" placeholder="URL Imagen" value="${plato.imagen}">`,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Guardar',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value;
        const descripcion = (document.getElementById('descripcion') as HTMLInputElement).value;
        const precio = parseFloat((document.getElementById('precio') as HTMLInputElement).value);
        const imagen = (document.getElementById('imagen') as HTMLInputElement).value;

        return { nombre, descripcion, precio, imagen };
      }
    }).then((result) => {
      if (result.isConfirmed) {
        const updatedPlato = {
          ...plato,
          nombre: result.value?.nombre,
          descripcion: result.value?.descripcion,
          precio: result.value?.precio,
          imagen: result.value?.imagen
        };

        this.platoService.actualizarPlato(plato.id, updatedPlato).subscribe({
          next: () => {
            Swal.fire('Actualizado', 'El plato ha sido actualizado correctamente.', 'success');
            this.cargarPlatos();
          },
          error: () => {
            Swal.fire('Error', 'No se pudo actualizar el plato.', 'error');
          }
        });
      }
    });
  }

  abrirModalCrear(): void {
    const opcionesCategorias = this.categorias.map(cat =>
      `<option value="${cat.id}">${cat.nombre}</option>`
    ).join('');

    Swal.fire({
      title: 'Añadir nuevo plato',
      html:
        `<input id="nombre" class="swal2-input" placeholder="Nombre">
         <input id="descripcion" class="swal2-input" placeholder="Descripción">
         <input id="precio" type="number" class="swal2-input" placeholder="Precio">
         <input id="imagen" class="swal2-input" placeholder="URL Imagen">
         <select id="categoria" class="swal2-select">
           <option disabled selected>Selecciona una categoría</option>
           ${opcionesCategorias}
         </select>`,
      focusConfirm: false,
      showCancelButton: true,
      confirmButtonText: 'Crear',
      preConfirm: () => {
        const nombre = (document.getElementById('nombre') as HTMLInputElement).value;
        const descripcion = (document.getElementById('descripcion') as HTMLInputElement).value;
        const precio = parseFloat((document.getElementById('precio') as HTMLInputElement).value);
        const imagen = (document.getElementById('imagen') as HTMLInputElement).value;
        const idCategoria = parseInt((document.getElementById('categoria') as HTMLSelectElement).value);

        if (!nombre || !descripcion || isNaN(precio) || isNaN(idCategoria)) {
          Swal.showValidationMessage('Todos los campos son obligatorios');
        }

        return { nombre, descripcion, precio, imagen, idCategoria, disponibleEnCarta: true };
      }
    }).then((result) => {
      if (result.isConfirmed) {
        this.platoService.crearPlato(result.value).subscribe({
          next: () => {
            Swal.fire('Creado', 'El plato ha sido añadido correctamente.', 'success');
            this.cargarPlatos();
          },
          error: () => {
            Swal.fire('Error', 'No se pudo crear el plato.', 'error');
          }
        });
      }
    });
  }

  confirmarEliminarPlato(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el plato de forma permanente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.platoService.eliminarPlato(id).subscribe({
          next: () => {
            Swal.fire('Eliminado', 'El plato ha sido eliminado correctamente.', 'success');
            this.cargarPlatos(); // Recargar la lista
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar el plato.', 'error');
          }
        });
      }
    });
  }



}
