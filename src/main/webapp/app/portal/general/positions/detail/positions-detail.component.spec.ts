import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PositionsDetailPortalComponent } from './positions-detail.component';

describe('Component Tests', () => {
  describe('Positions Management Detail Component', () => {
    let comp: PositionsDetailPortalComponent;
    let fixture: ComponentFixture<PositionsDetailPortalComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PositionsDetailPortalComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ positions: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PositionsDetailPortalComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PositionsDetailPortalComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load positions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.positions).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
