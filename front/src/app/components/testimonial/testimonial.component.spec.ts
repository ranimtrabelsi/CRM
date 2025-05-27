import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TESTIMONIALComponent } from './testimonial.component';

describe('TESTIMONIALComponent', () => {
  let component: TESTIMONIALComponent;
  let fixture: ComponentFixture<TESTIMONIALComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TESTIMONIALComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TESTIMONIALComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
