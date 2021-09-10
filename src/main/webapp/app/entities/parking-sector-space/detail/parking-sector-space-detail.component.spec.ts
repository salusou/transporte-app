import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParkingSectorSpaceDetailComponent } from './parking-sector-space-detail.component';

describe('Component Tests', () => {
  describe('ParkingSectorSpace Management Detail Component', () => {
    let comp: ParkingSectorSpaceDetailComponent;
    let fixture: ComponentFixture<ParkingSectorSpaceDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ParkingSectorSpaceDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ parkingSectorSpace: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ParkingSectorSpaceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParkingSectorSpaceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load parkingSectorSpace on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parkingSectorSpace).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
