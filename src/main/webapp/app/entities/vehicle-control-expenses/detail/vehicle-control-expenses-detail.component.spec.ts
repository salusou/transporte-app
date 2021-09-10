import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleControlExpensesDetailComponent } from './vehicle-control-expenses-detail.component';

describe('Component Tests', () => {
  describe('VehicleControlExpenses Management Detail Component', () => {
    let comp: VehicleControlExpensesDetailComponent;
    let fixture: ComponentFixture<VehicleControlExpensesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleControlExpensesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleControlExpenses: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleControlExpensesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleControlExpensesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleControlExpenses on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleControlExpenses).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
