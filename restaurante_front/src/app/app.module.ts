import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';

import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { CartaComponent } from './pages/carta/carta.component';
import { LoginComponent } from './pages/login/login.component';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RegistroComponent } from './pages/registro/registro.component';
import { ReservasComponent } from './pages/reservas/reservas.component';
import { MisReservasComponent } from './pages/mis-reservas/mis-reservas.component';
import { OpinionCarruselComponent } from './components/opinion-carrusel/opinion-carrusel.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AdminUsuariosComponent } from './pages/admin/admin-usuarios/admin-usuarios.component';
import { AdminReservasComponent } from './pages/admin/admin-reservas/admin-reservas.component';
import { AdminCartaComponent } from './pages/admin/admin-carta/admin-carta.component';
import { AdminMesasComponent } from './pages/admin/admin-mesas/admin-mesas.component';
// importa aquí los demás cuando los crees

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CartaComponent,
    LoginComponent,
    RegistroComponent,
    ReservasComponent,
    MisReservasComponent,
    OpinionCarruselComponent,
    AdminComponent,
    AdminUsuariosComponent,
    AdminReservasComponent,
    AdminCartaComponent,
    AdminMesasComponent


    // otros componentes...
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
