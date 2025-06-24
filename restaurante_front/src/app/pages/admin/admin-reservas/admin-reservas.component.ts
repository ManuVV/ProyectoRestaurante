import { Component, OnInit } from '@angular/core';
import { ReservaService } from '../../../core/services/reserva.service';
import { Reserva } from '../../../models/reservas-model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-reservas',
  templateUrl: './admin-reservas.component.html',
  styleUrls: ['./admin-reservas.component.scss']
})
export class AdminReservasComponent implements OnInit {

  reservas: Reserva[] = [];

  constructor(private reservaService: ReservaService) {}

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas(): void {
    this.reservaService.listarReservas().subscribe({
      next: (data) => {
        this.reservas = data;
      },
      error: (error) => {
        console.error('Error al cargar reservas', error);
      }
    });
  }


  confirmarEliminar(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta reserva se eliminará permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservaService.eliminarReservaComoAdmin(id).subscribe(() => {
          Swal.fire('Eliminada', 'La reserva ha sido eliminada.', 'success');
          this.cargarReservas();
        });
      }
    });
  }

  formatearHora(hora: string | Date): string {
    if (hora instanceof Date) {
      return hora.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    }
    return hora ? hora.slice(0, 5) : '';
  }
}
