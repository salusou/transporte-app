import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleInspectionsDetailComponent } from './vehicle-inspections-detail.component';

describe('Component Tests', () => {
  describe('VehicleInspections Management Detail Component', () => {
    let comp: VehicleInspectionsDetailComponent;
    let fixture: ComponentFixture<VehicleInspectionsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleInspectionsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleInspections: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleInspectionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleInspectionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleInspections on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleInspections).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
