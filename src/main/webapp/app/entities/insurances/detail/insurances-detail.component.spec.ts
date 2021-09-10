import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InsurancesDetailComponent } from './insurances-detail.component';

describe('Component Tests', () => {
  describe('Insurances Management Detail Component', () => {
    let comp: InsurancesDetailComponent;
    let fixture: ComponentFixture<InsurancesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [InsurancesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ insurances: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(InsurancesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsurancesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load insurances on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.insurances).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
