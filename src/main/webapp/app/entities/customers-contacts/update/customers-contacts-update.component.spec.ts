jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomersContactsService } from '../service/customers-contacts.service';
import { ICustomersContacts, CustomersContacts } from '../customers-contacts.model';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';

import { CustomersContactsUpdateComponent } from './customers-contacts-update.component';

describe('Component Tests', () => {
  describe('CustomersContacts Management Update Component', () => {
    let comp: CustomersContactsUpdateComponent;
    let fixture: ComponentFixture<CustomersContactsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let customersContactsService: CustomersContactsService;
    let customersService: CustomersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomersContactsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CustomersContactsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomersContactsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      customersContactsService = TestBed.inject(CustomersContactsService);
      customersService = TestBed.inject(CustomersService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Customers query and add missing value', () => {
        const customersContacts: ICustomersContacts = { id: 456 };
        const customers: ICustomers = { id: 89459 };
        customersContacts.customers = customers;

        const customersCollection: ICustomers[] = [{ id: 24271 }];
        jest.spyOn(customersService, 'query').mockReturnValue(of(new HttpResponse({ body: customersCollection })));
        const additionalCustomers = [customers];
        const expectedCollection: ICustomers[] = [...additionalCustomers, ...customersCollection];
        jest.spyOn(customersService, 'addCustomersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customersContacts });
        comp.ngOnInit();

        expect(customersService.query).toHaveBeenCalled();
        expect(customersService.addCustomersToCollectionIfMissing).toHaveBeenCalledWith(customersCollection, ...additionalCustomers);
        expect(comp.customersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const customersContacts: ICustomersContacts = { id: 456 };
        const customers: ICustomers = { id: 65402 };
        customersContacts.customers = customers;

        activatedRoute.data = of({ customersContacts });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(customersContacts));
        expect(comp.customersSharedCollection).toContain(customers);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomersContacts>>();
        const customersContacts = { id: 123 };
        jest.spyOn(customersContactsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customersContacts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customersContacts }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(customersContactsService.update).toHaveBeenCalledWith(customersContacts);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomersContacts>>();
        const customersContacts = new CustomersContacts();
        jest.spyOn(customersContactsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customersContacts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customersContacts }));
        saveSubject.complete();

        // THEN
        expect(customersContactsService.create).toHaveBeenCalledWith(customersContacts);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomersContacts>>();
        const customersContacts = { id: 123 };
        jest.spyOn(customersContactsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customersContacts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(customersContactsService.update).toHaveBeenCalledWith(customersContacts);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCustomersById', () => {
        it('Should return tracked Customers primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCustomersById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
