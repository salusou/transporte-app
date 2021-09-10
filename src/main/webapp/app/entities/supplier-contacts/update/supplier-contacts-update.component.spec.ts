jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SupplierContactsService } from '../service/supplier-contacts.service';
import { ISupplierContacts, SupplierContacts } from '../supplier-contacts.model';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';

import { SupplierContactsUpdateComponent } from './supplier-contacts-update.component';

describe('Component Tests', () => {
  describe('SupplierContacts Management Update Component', () => {
    let comp: SupplierContactsUpdateComponent;
    let fixture: ComponentFixture<SupplierContactsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let supplierContactsService: SupplierContactsService;
    let suppliersService: SuppliersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SupplierContactsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SupplierContactsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierContactsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      supplierContactsService = TestBed.inject(SupplierContactsService);
      suppliersService = TestBed.inject(SuppliersService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Suppliers query and add missing value', () => {
        const supplierContacts: ISupplierContacts = { id: 456 };
        const suppliers: ISuppliers = { id: 61278 };
        supplierContacts.suppliers = suppliers;

        const suppliersCollection: ISuppliers[] = [{ id: 84929 }];
        jest.spyOn(suppliersService, 'query').mockReturnValue(of(new HttpResponse({ body: suppliersCollection })));
        const additionalSuppliers = [suppliers];
        const expectedCollection: ISuppliers[] = [...additionalSuppliers, ...suppliersCollection];
        jest.spyOn(suppliersService, 'addSuppliersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ supplierContacts });
        comp.ngOnInit();

        expect(suppliersService.query).toHaveBeenCalled();
        expect(suppliersService.addSuppliersToCollectionIfMissing).toHaveBeenCalledWith(suppliersCollection, ...additionalSuppliers);
        expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const supplierContacts: ISupplierContacts = { id: 456 };
        const suppliers: ISuppliers = { id: 7225 };
        supplierContacts.suppliers = suppliers;

        activatedRoute.data = of({ supplierContacts });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(supplierContacts));
        expect(comp.suppliersSharedCollection).toContain(suppliers);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SupplierContacts>>();
        const supplierContacts = { id: 123 };
        jest.spyOn(supplierContactsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ supplierContacts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: supplierContacts }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(supplierContactsService.update).toHaveBeenCalledWith(supplierContacts);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SupplierContacts>>();
        const supplierContacts = new SupplierContacts();
        jest.spyOn(supplierContactsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ supplierContacts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: supplierContacts }));
        saveSubject.complete();

        // THEN
        expect(supplierContactsService.create).toHaveBeenCalledWith(supplierContacts);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SupplierContacts>>();
        const supplierContacts = { id: 123 };
        jest.spyOn(supplierContactsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ supplierContacts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(supplierContactsService.update).toHaveBeenCalledWith(supplierContacts);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackSuppliersById', () => {
        it('Should return tracked Suppliers primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSuppliersById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
