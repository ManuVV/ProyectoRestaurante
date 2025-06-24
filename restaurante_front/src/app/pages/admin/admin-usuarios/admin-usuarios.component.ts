import { Usuario } from '../../../models/usuario-model';
import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../../core/services/usuario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-usuarios',
  templateUrl: './admin-usuarios.component.html',
  styleUrls: ['./admin-usuarios.component.scss']
})
export class AdminUsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
    this.usuarioService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
      },
      error: (err) => {
        console.error('Error al cargar usuarios:', err);
      }
    });
  }

  cargarUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
      },
      error: (err) => {
        console.error('Error al cargar usuarios:', err);
      }
    });
  }


confirmarEliminarUsuario(id: number) {
  Swal.fire({
    title: '¿Estás seguro?',
    text: 'Esta acción eliminará el usuario de forma permanente.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#c21807',
    cancelButtonColor: '#6c757d',
    confirmButtonText: 'Sí, eliminar'
  }).then((result) => {
    if (result.isConfirmed) {
      this.usuarioService.eliminarUsuario(id).subscribe({
        next: () => {
          Swal.fire('Eliminado', 'El usuario ha sido eliminado.', 'success');
          this.cargarUsuarios(); // recarga lista
        },
        error: (err) => {
          Swal.fire('Error', 'No se pudo eliminar el usuario.', 'error');
        }
      });
    }
  });
}

cambiarEstadoUsuario(id: number): void {
  this.usuarioService.cambiarEstadoUsuario(id).subscribe({
    next: () => {
      Swal.fire('Éxito', 'El estado del usuario ha cambiado.', 'success');
      this.cargarUsuarios(); // Recargar la lista
    },
    error: (err) => {
      Swal.fire('Error', 'No se pudo cambiar el estado del usuario.', 'error');
    }
  });
}

}
