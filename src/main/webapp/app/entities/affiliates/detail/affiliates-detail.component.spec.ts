import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AffiliatesDetailComponent } from './affiliates-detail.component';

describe('Component Tests', () => {
  describe('Affiliates Management Detail Component', () => {
    let comp: AffiliatesDetailComponent;
    let fixture: ComponentFixture<AffiliatesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AffiliatesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ affiliates: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AffiliatesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AffiliatesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load affiliates on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.affiliates).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
