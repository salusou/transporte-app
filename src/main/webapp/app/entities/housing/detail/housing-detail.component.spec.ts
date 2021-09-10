import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HousingDetailComponent } from './housing-detail.component';

describe('Component Tests', () => {
  describe('Housing Management Detail Component', () => {
    let comp: HousingDetailComponent;
    let fixture: ComponentFixture<HousingDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HousingDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ housing: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HousingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HousingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load housing on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.housing).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
