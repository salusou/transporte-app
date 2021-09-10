import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleControlBillingDetailComponent } from './vehicle-control-billing-detail.component';

describe('Component Tests', () => {
  describe('VehicleControlBilling Management Detail Component', () => {
    let comp: VehicleControlBillingDetailComponent;
    let fixture: ComponentFixture<VehicleControlBillingDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleControlBillingDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleControlBilling: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleControlBillingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleControlBillingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleControlBilling on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleControlBilling).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
