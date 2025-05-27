import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TecknicalListComponent } from './tecknical-list.component';

describe('TecknicalListComponent', () => {
  let component: TecknicalListComponent;
  let fixture: ComponentFixture<TecknicalListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TecknicalListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TecknicalListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
