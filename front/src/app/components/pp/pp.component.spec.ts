import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PpComponent } from './pp.component';

describe('PpComponent', () => {
  let component: PpComponent;
  let fixture: ComponentFixture<PpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PpComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
