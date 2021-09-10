import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParkingSectorDetailComponent } from './parking-sector-detail.component';

describe('Component Tests', () => {
  describe('ParkingSector Management Detail Component', () => {
    let comp: ParkingSectorDetailComponent;
    let fixture: ComponentFixture<ParkingSectorDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ParkingSectorDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ parkingSector: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ParkingSectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParkingSectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load parkingSector on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parkingSector).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
