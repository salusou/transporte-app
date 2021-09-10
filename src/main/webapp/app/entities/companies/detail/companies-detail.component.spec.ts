import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompaniesDetailComponent } from './companies-detail.component';

describe('Component Tests', () => {
  describe('Companies Management Detail Component', () => {
    let comp: CompaniesDetailComponent;
    let fixture: ComponentFixture<CompaniesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CompaniesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ companies: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CompaniesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompaniesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load companies on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companies).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
