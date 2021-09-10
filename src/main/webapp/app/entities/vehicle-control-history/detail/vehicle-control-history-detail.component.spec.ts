import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleControlHistoryDetailComponent } from './vehicle-control-history-detail.component';

describe('Component Tests', () => {
  describe('VehicleControlHistory Management Detail Component', () => {
    let comp: VehicleControlHistoryDetailComponent;
    let fixture: ComponentFixture<VehicleControlHistoryDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VehicleControlHistoryDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vehicleControlHistory: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VehicleControlHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleControlHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehicleControlHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleControlHistory).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
