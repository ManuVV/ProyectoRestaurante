import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../core/services/auth.service';
import { Mesa } from '../../models/mesa-model';
import { MesaService } from '../../core/services/mesa.service';
import { ReservaService } from '../../core/services/reserva.service';

@Component({
  selector: 'app-reservas',
  templateUrl: './reservas.component.html',
  styleUrls: ['./reservas.component.scss']
})
export class ReservasComponent implements OnInit {
  nombre: string = '';
  fecha: string | null = null;

  horaSeleccionada: string = '';
  horariosDisponibles: string[] = [];
  numeroPersonas: number = 1;
  mesas: Mesa[] = [];
  idMesaSeleccionada: number = 1; // Valor por defecto
  cargandoReserva: boolean = false;
  cargandoHorarios: boolean = false;
  minFecha: string = '';


  constructor(private http: HttpClient, private router: Router, private authService: AuthService, private mesaService: MesaService, private reservaService: ReservaService) { }

  ngOnInit(): void {
    if (!this.authService.estaLogueado()) {
      Swal.fire({
        icon: 'warning',
        title: 'Acceso restringido',
        text: 'Debes iniciar sesión para hacer una reserva.',
        timer: 3000,
        showConfirmButton: false
      }).then(() => {
        this.router.navigate(['/login']);
      });
    }
    this.minFecha = this.obtenerFechaActual();
    this.cargarMesas();
  }

  cargarHorariosDisponibles() {
    if (!this.fecha) return;

    this.cargandoHorarios = true;

    this.reservaService.obtenerHorariosDisponibles(this.fecha, this.idMesaSeleccionada).subscribe({
      next: (horarios) => {
        this.horariosDisponibles = horarios;
        this.horaSeleccionada = '';
        this.cargandoHorarios = false;
      },
      error: (error) => {
        console.error('Error al cargar horarios:', error);
        this.cargandoHorarios = false;
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron cargar los horarios disponibles.'
        });
      }
    });
  }

  cargarMesas() {
    this.mesaService.listarMesas().subscribe({
      next: (mesas) => {
        this.mesas = mesas;
      },
      error: (error) => {
        console.error('Error al cargar mesas:', error);
      }
    });
  }

  get mesasActivas(): Mesa[] {
    return this.mesas.filter(m => m.activa);
  }

  crearReserva() {
    if (!this.nombre || !this.fecha || !this.horaSeleccionada || !this.numeroPersonas) {
      Swal.fire({
        icon: 'warning',
        title: 'Campos incompletos',
        text: 'Por favor, completa todos los campos.'
      });
      return;
    }

    if (this.numeroPersonas > 8 || this.numeroPersonas < 1) {
      Swal.fire({
        icon: 'warning',
        title: 'Número no permitido',
        text: 'Solo se pueden resevar entre un rango de 1 a 8 personas'
      });
      return;
    }

    this.cargandoReserva = true; // ⏳ Empezamos a cargar

    const reservaData = {
      nombre: this.nombre,
      fecha: this.fecha,
      hora: this.horaSeleccionada,
      numeroPersonas: this.numeroPersonas,
      idMesa: this.idMesaSeleccionada
    };

    this.reservaService.crearReserva(reservaData).subscribe({
      next: () => {
        this.cargandoReserva = false;
        Swal.fire({
          icon: 'success',
          title: 'Reserva confirmada',
          text: 'Tu reserva se ha realizado con éxito',
          timer: 2000,
          showConfirmButton: false
        }).then(() => {
          this.router.navigate(['/']);
        });
      },
      error: (error) => {
        this.cargandoReserva = false;
        console.error('Error al reservar:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error al reservar',
          text: 'No se pudo realizar la reserva. Inténtalo más tarde.'
        });
      }
    });
  }

  obtenerFechaActual(): string {
    const hoy = new Date();
    const year = hoy.getFullYear();
    const month = (hoy.getMonth() + 1).toString().padStart(2, '0');
    const day = hoy.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
