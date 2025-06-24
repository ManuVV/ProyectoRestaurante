import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpinionCarruselComponent } from './opinion-carrusel.component';

describe('OpinionCarruselComponent', () => {
  let component: OpinionCarruselComponent;
  let fixture: ComponentFixture<OpinionCarruselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OpinionCarruselComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpinionCarruselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
