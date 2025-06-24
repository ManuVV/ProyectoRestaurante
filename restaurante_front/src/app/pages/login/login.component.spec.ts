import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const authSpy = jasmine.createSpyObj('AuthService', ['login', 'guardarToken', 'esAdmin']);
    const routerSpyObj = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [FormsModule],
      providers: [
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routerSpyObj }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authServiceSpy = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Debe mostrar un error si el email o la contraseña no se han introducido', () => {
    component.email = '';
    component.password = '';
    component.login();

    expect(component.error).toBe('Debes completar todos los campos.');
    expect(component.cargando).toBeFalse();
    expect(authServiceSpy.login).not.toHaveBeenCalled();
  });

  it('Debe llamar a  AuthService.login si el formulario es valido', () => {
    component.email = 'test@example.com';
    component.password = '123456';

    // Simula que el observable devuelve algo
    authServiceSpy.login.and.returnValue(of({ token: 'fake-token' }));
    authServiceSpy.esAdmin.and.returnValue(false);

    component.login();

    expect(authServiceSpy.login).toHaveBeenCalledWith('test@example.com', '123456');
  });
});
