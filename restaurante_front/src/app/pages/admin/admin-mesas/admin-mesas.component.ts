import { Component, OnInit } from '@angular/core';
import { MesaService } from '../../../core/services/mesa.service';
import Swal from 'sweetalert2';
import { Mesa } from '../../../models/mesa-model';

@Component({
  selector: 'app-admin-mesas',
  templateUrl: './admin-mesas.component.html',
  styleUrls: ['./admin-mesas.component.scss']
})
export class AdminMesasComponent implements OnInit {

  mesas: Mesa[] = [];

  constructor(private mesaService: MesaService) {}

  ngOnInit(): void {
    this.cargarMesas();
  }

  cargarMesas(): void {
    this.mesaService.listarMesas().subscribe((data) => {
      this.mesas = data;
    });
  }

  abrirModalCrear(): void {
    Swal.fire({
      title: 'Añadir nueva mesa',
      input: 'text',
      inputLabel: 'Nombre de la mesa',
      inputPlaceholder: 'Ejemplo: Mesa 1',
      showCancelButton: true,
      confirmButtonText: 'Crear',
      preConfirm: (nombre) => {
        if (!nombre) {
          Swal.showValidationMessage('El nombre es obligatorio');
        }
        return nombre;
      }
    }).then((result) => {
      if (result.isConfirmed) {
        this.mesaService.crearMesa(result.value).subscribe({
          next: () => {
            Swal.fire('Creada', 'La mesa ha sido añadida correctamente.', 'success');
            this.cargarMesas();
          },
          error: () => {
            Swal.fire('Error', 'No se pudo crear la mesa.', 'error');
          }
        });
      }
    });
  }

  cambiarEstado(mesa: Mesa): void {
    this.mesaService.cambiarEstadoMesa(mesa.id).subscribe(() => {
      Swal.fire('Actualizado', 'El estado de la mesa ha cambiado.', 'success');
      this.cargarMesas();
    });
  }

  confirmarEliminar(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará la mesa permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.mesaService.eliminarMesa(id).subscribe(() => {
          Swal.fire('Eliminada', 'La mesa ha sido eliminada.', 'success');
          this.cargarMesas();
        });
      }
    });
  }
}
