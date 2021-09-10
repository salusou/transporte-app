import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuppliersDetailComponent } from './suppliers-detail.component';

describe('Component Tests', () => {
  describe('Suppliers Management Detail Component', () => {
    let comp: SuppliersDetailComponent;
    let fixture: ComponentFixture<SuppliersDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SuppliersDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ suppliers: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SuppliersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuppliersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load suppliers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.suppliers).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
