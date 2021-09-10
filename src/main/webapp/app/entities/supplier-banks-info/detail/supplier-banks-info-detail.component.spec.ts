import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SupplierBanksInfoDetailComponent } from './supplier-banks-info-detail.component';

describe('Component Tests', () => {
  describe('SupplierBanksInfo Management Detail Component', () => {
    let comp: SupplierBanksInfoDetailComponent;
    let fixture: ComponentFixture<SupplierBanksInfoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SupplierBanksInfoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ supplierBanksInfo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SupplierBanksInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierBanksInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supplierBanksInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierBanksInfo).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
