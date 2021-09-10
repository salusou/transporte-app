import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HousingVehicleItemDetailComponent } from './housing-vehicle-item-detail.component';

describe('Component Tests', () => {
  describe('HousingVehicleItem Management Detail Component', () => {
    let comp: HousingVehicleItemDetailComponent;
    let fixture: ComponentFixture<HousingVehicleItemDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HousingVehicleItemDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ housingVehicleItem: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HousingVehicleItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HousingVehicleItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load housingVehicleItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.housingVehicleItem).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
