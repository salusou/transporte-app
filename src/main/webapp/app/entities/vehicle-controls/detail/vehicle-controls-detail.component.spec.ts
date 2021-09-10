import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleControlsDetailComponent } from './vehicle-controls-detail.component';

describe('Component Tests', () => {
  describe('VehicleControls Management Detail Component', () => {
    let comp: VehicleControlsDetailComponent;
    let fixture: ComponentFixture<VehicleControlsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleControlsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleControls: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleControlsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleControlsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleControls on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleControls).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
