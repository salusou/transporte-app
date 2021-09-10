import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleControlItemDetailComponent } from './vehicle-control-item-detail.component';

describe('Component Tests', () => {
  describe('VehicleControlItem Management Detail Component', () => {
    let comp: VehicleControlItemDetailComponent;
    let fixture: ComponentFixture<VehicleControlItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleControlItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleControlItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleControlItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleControlItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleControlItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleControlItem).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
