import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../core/services/auth.service';
import { ReservaService } from '../../core/services/reserva.service';
import { Reserva } from '../../models/reservas-model';
import Swal from 'sweetalert2';

/**
 * Componente que muestra las reservas realizadas por el usuario autenticado.
 * Permite cancelar reservas con confirmación.
 */
@Component({
  selector: 'app-mis-reservas',
  templateUrl: './mis-reservas.component.html',
  styleUrls: ['./mis-reservas.component.scss']
})
export class MisReservasComponent implements OnInit {

  reservas: Reserva[] = [];
  cargando: boolean = true;
  error: boolean = false;

  constructor(private http: HttpClient, private authService: AuthService,  private reservaService: ReservaService) {}

  /**
   * Al iniciar el componente, se comprueba si el usuario está logueado.
   * Si no lo está, se redirige al login. Si lo está, se cargan sus reservas.
   */
  ngOnInit(): void {
    if (!this.authService.estaLogueado()) {
      window.location.href = '/login';
      return;
    }

    this.cargarMisReservas();
  }

  /**
   * Llama al servicio para obtener las reservas del usuario actual.
   * Convierte los campos `fecha` y `hora` a objetos `Date` para formateo.
   */
  cargarMisReservas(): void {
    this.reservaService.listarMisReservas().subscribe({
      next: (data) => {
        this.reservas = data.map(reserva => ({
          ...reserva,
          fecha: new Date(reserva.fecha),
          hora: this.convertirHoraAFecha(reserva.hora)
        }));
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar reservas:', err);
        this.error = true;
        this.cargando = false;
      }
    });
  }

  /**
   * Convierte una cadena de hora (`"HH:mm:ss"`) a un objeto `Date`.
   *
   * @param horaInput hora en formato string o Date
   * @returns objeto `Date` con la hora establecida
   */
  convertirHoraAFecha(horaInput: string | Date): Date {
    if (horaInput instanceof Date) {
      return horaInput;
    }

    const [hours, minutes, seconds] = horaInput.split(':');
    const date = new Date();
    date.setHours(+hours);
    date.setMinutes(+minutes);
    date.setSeconds(+seconds);
    return date;
  }

  /**
   * Lanza un cuadro de confirmación para cancelar una reserva.
   * Si se confirma, elimina la reserva del backend y la lista local.
   *
   * @param idReserva identificador de la reserva a cancelar
   */
  cancelarReserva(idReserva: number): void {
    Swal.fire({
      title: '¿Cancelar reserva?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.reservaService.cancelarMiReserva(idReserva).subscribe({
          next: () => {
            Swal.fire(
              'Cancelado',
              'Tu reserva ha sido cancelada correctamente.',
              'success'
            );
            // Eliminar la reserva de la lista localmente sin recargar
            this.reservas = this.reservas.filter(r => r.id !== idReserva);
          },
          error: (error) => {
            console.error('Error al cancelar reserva:', error);
            Swal.fire(
              'Error',
              'No se pudo cancelar la reserva. Inténtalo más tarde.',
              'error'
            );
          }
        });
      }
    });
  }

}
