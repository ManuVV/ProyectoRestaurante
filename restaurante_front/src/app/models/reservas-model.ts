export interface Reserva {
  id: number;
  nombre: string;
  fecha: string | Date;
  hora: string | Date;
  numeroPersonas: number;
  idMesa: number;
  nombreMesa: string;
  nombreUsuario: string;
  emailUsuario: string;
}
