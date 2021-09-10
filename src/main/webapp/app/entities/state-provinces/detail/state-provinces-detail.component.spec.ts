import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StateProvincesDetailComponent } from './state-provinces-detail.component';

describe('Component Tests', () => {
  describe('StateProvinces Management Detail Component', () => {
    let comp: StateProvincesDetailComponent;
    let fixture: ComponentFixture<StateProvincesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [StateProvincesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ stateProvinces: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(StateProvincesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateProvincesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stateProvinces on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stateProvinces).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
