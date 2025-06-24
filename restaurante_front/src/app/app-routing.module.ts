import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CartaComponent } from './pages/carta/carta.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistroComponent } from './pages/registro/registro.component';
import { ReservasComponent } from './pages/reservas/reservas.component';
import { MisReservasComponent } from './pages/mis-reservas/mis-reservas.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AdminUsuariosComponent } from './pages/admin/admin-usuarios/admin-usuarios.component';
import { AdminCartaComponent } from './pages/admin/admin-carta/admin-carta.component';
import { AdminReservasComponent } from './pages/admin/admin-reservas/admin-reservas.component';
import { AdminMesasComponent } from './pages/admin/admin-mesas/admin-mesas.component';
import { AdminGuard } from './guards/admin.guard';
import { UsuarioGuard } from './guards/usuario.guard';

const routes: Routes = [
  { path: '', component: HomeComponent, data: { animation: 'HomePage' } },
  { path: 'carta', component: CartaComponent, data: { animation: 'CartaPage' } },
  { path: 'login', component: LoginComponent, data: { animation: 'LoginPage' } },
  { path: 'reservas', component: ReservasComponent, data: { animation: 'ReservasPage' } },
  { path: 'registrarse', component: RegistroComponent, data: { animation: 'RegistroPage' } },
  { path: 'mis-reservas', component: MisReservasComponent, canActivate: [UsuarioGuard] },

  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminGuard],
    children: [
      { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
      { path: 'usuarios', component: AdminUsuariosComponent },
      { path: 'platos', component: AdminCartaComponent },
      { path: 'reservas', component: AdminReservasComponent },
      { path: 'mesas', component: AdminMesasComponent}
    ]
  },

  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
