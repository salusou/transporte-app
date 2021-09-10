import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CitiesDetailComponent } from './cities-detail.component';

describe('Component Tests', () => {
  describe('Cities Management Detail Component', () => {
    let comp: CitiesDetailComponent;
    let fixture: ComponentFixture<CitiesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CitiesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cities: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CitiesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CitiesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cities on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cities).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
