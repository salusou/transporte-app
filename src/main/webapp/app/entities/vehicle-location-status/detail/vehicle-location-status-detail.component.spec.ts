import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleLocationStatusDetailComponent } from './vehicle-location-status-detail.component';

describe('Component Tests', () => {
  describe('VehicleLocationStatus Management Detail Component', () => {
    let comp: VehicleLocationStatusDetailComponent;
    let fixture: ComponentFixture<VehicleLocationStatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleLocationStatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleLocationStatus: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleLocationStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleLocationStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleLocationStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleLocationStatus).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
