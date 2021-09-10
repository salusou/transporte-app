import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SupplierContactsDetailComponent } from './supplier-contacts-detail.component';

describe('Component Tests', () => {
  describe('SupplierContacts Management Detail Component', () => {
    let comp: SupplierContactsDetailComponent;
    let fixture: ComponentFixture<SupplierContactsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SupplierContactsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ supplierContacts: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SupplierContactsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierContactsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supplierContacts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierContacts).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
