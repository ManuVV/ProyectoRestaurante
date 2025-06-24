import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMesasComponent } from './admin-mesas.component';

describe('AdminMesasComponent', () => {
  let component: AdminMesasComponent;
  let fixture: ComponentFixture<AdminMesasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminMesasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminMesasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
