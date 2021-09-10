import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomersContactsDetailComponent } from './customers-contacts-detail.component';

describe('Component Tests', () => {
  describe('CustomersContacts Management Detail Component', () => {
    let comp: CustomersContactsDetailComponent;
    let fixture: ComponentFixture<CustomersContactsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CustomersContactsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ customersContacts: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CustomersContactsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomersContactsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customersContacts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customersContacts).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
