import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdministrativeFeesRangesDetailComponent } from './administrative-fees-ranges-detail.component';

describe('Component Tests', () => {
  describe('AdministrativeFeesRanges Management Detail Component', () => {
    let comp: AdministrativeFeesRangesDetailComponent;
    let fixture: ComponentFixture<AdministrativeFeesRangesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AdministrativeFeesRangesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ administrativeFeesRanges: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AdministrativeFeesRangesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdministrativeFeesRangesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load administrativeFeesRanges on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.administrativeFeesRanges).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
