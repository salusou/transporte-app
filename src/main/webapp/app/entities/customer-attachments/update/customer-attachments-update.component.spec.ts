jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomerAttachmentsService } from '../service/customer-attachments.service';
import { ICustomerAttachments, CustomerAttachments } from '../customer-attachments.model';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';
import { IStatusAttachments } from 'app/entities/status-attachments/status-attachments.model';
import { StatusAttachmentsService } from 'app/entities/status-attachments/service/status-attachments.service';

import { CustomerAttachmentsUpdateComponent } from './customer-attachments-update.component';

describe('Component Tests', () => {
  describe('CustomerAttachments Management Update Component', () => {
    let comp: CustomerAttachmentsUpdateComponent;
    let fixture: ComponentFixture<CustomerAttachmentsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let customerAttachmentsService: CustomerAttachmentsService;
    let customersService: CustomersService;
    let statusAttachmentsService: StatusAttachmentsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomerAttachmentsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CustomerAttachmentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerAttachmentsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      customerAttachmentsService = TestBed.inject(CustomerAttachmentsService);
      customersService = TestBed.inject(CustomersService);
      statusAttachmentsService = TestBed.inject(StatusAttachmentsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Customers query and add missing value', () => {
        const customerAttachments: ICustomerAttachments = { id: 456 };
        const customers: ICustomers = { id: 63415 };
        customerAttachments.customers = customers;

        const customersCollection: ICustomers[] = [{ id: 34459 }];
        jest.spyOn(customersService, 'query').mockReturnValue(of(new HttpResponse({ body: customersCollection })));
        const additionalCustomers = [customers];
        const expectedCollection: ICustomers[] = [...additionalCustomers, ...customersCollection];
        jest.spyOn(customersService, 'addCustomersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customerAttachments });
        comp.ngOnInit();

        expect(customersService.query).toHaveBeenCalled();
        expect(customersService.addCustomersToCollectionIfMissing).toHaveBeenCalledWith(customersCollection, ...additionalCustomers);
        expect(comp.customersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call StatusAttachments query and add missing value', () => {
        const customerAttachments: ICustomerAttachments = { id: 456 };
        const statusAttachments: IStatusAttachments = { id: 31563 };
        customerAttachments.statusAttachments = statusAttachments;

        const statusAttachmentsCollection: IStatusAttachments[] = [{ id: 47956 }];
        jest.spyOn(statusAttachmentsService, 'query').mockReturnValue(of(new HttpResponse({ body: statusAttachmentsCollection })));
        const additionalStatusAttachments = [statusAttachments];
        const expectedCollection: IStatusAttachments[] = [...additionalStatusAttachments, ...statusAttachmentsCollection];
        jest.spyOn(statusAttachmentsService, 'addStatusAttachmentsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customerAttachments });
        comp.ngOnInit();

        expect(statusAttachmentsService.query).toHaveBeenCalled();
        expect(statusAttachmentsService.addStatusAttachmentsToCollectionIfMissing).toHaveBeenCalledWith(
          statusAttachmentsCollection,
          ...additionalStatusAttachments
        );
        expect(comp.statusAttachmentsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const customerAttachments: ICustomerAttachments = { id: 456 };
        const customers: ICustomers = { id: 83309 };
        customerAttachments.customers = customers;
        const statusAttachments: IStatusAttachments = { id: 77914 };
        customerAttachments.statusAttachments = statusAttachments;

        activatedRoute.data = of({ customerAttachments });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(customerAttachments));
        expect(comp.customersSharedCollection).toContain(customers);
        expect(comp.statusAttachmentsSharedCollection).toContain(statusAttachments);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomerAttachments>>();
        const customerAttachments = { id: 123 };
        jest.spyOn(customerAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customerAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customerAttachments }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(customerAttachmentsService.update).toHaveBeenCalledWith(customerAttachments);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomerAttachments>>();
        const customerAttachments = new CustomerAttachments();
        jest.spyOn(customerAttachmentsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customerAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customerAttachments }));
        saveSubject.complete();

        // THEN
        expect(customerAttachmentsService.create).toHaveBeenCalledWith(customerAttachments);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomerAttachments>>();
        const customerAttachments = { id: 123 };
        jest.spyOn(customerAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customerAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(customerAttachmentsService.update).toHaveBeenCalledWith(customerAttachments);
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

      describe('trackStatusAttachmentsById', () => {
        it('Should return tracked StatusAttachments primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStatusAttachmentsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
