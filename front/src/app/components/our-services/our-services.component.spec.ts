import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OURSERVICESComponent } from './our-services.component';

describe('OURSERVICESComponent', () => {
  let component: OURSERVICESComponent;
  let fixture: ComponentFixture<OURSERVICESComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OURSERVICESComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OURSERVICESComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
