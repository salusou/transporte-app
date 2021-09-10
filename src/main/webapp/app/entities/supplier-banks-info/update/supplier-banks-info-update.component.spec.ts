jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SupplierBanksInfoService } from '../service/supplier-banks-info.service';
import { ISupplierBanksInfo, SupplierBanksInfo } from '../supplier-banks-info.model';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';

import { SupplierBanksInfoUpdateComponent } from './supplier-banks-info-update.component';

describe('Component Tests', () => {
  describe('SupplierBanksInfo Management Update Component', () => {
    let comp: SupplierBanksInfoUpdateComponent;
    let fixture: ComponentFixture<SupplierBanksInfoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let supplierBanksInfoService: SupplierBanksInfoService;
    let suppliersService: SuppliersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SupplierBanksInfoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SupplierBanksInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierBanksInfoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      supplierBanksInfoService = TestBed.inject(SupplierBanksInfoService);
      suppliersService = TestBed.inject(SuppliersService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Suppliers query and add missing value', () => {
        const supplierBanksInfo: ISupplierBanksInfo = { id: 456 };
        const suppliers: ISuppliers = { id: 58519 };
        supplierBanksInfo.suppliers = suppliers;

        const suppliersCollection: ISuppliers[] = [{ id: 45307 }];
        jest.spyOn(suppliersService, 'query').mockReturnValue(of(new HttpResponse({ body: suppliersCollection })));
        const additionalSuppliers = [suppliers];
        const expectedCollection: ISuppliers[] = [...additionalSuppliers, ...suppliersCollection];
        jest.spyOn(suppliersService, 'addSuppliersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ supplierBanksInfo });
        comp.ngOnInit();

        expect(suppliersService.query).toHaveBeenCalled();
        expect(suppliersService.addSuppliersToCollectionIfMissing).toHaveBeenCalledWith(suppliersCollection, ...additionalSuppliers);
        expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const supplierBanksInfo: ISupplierBanksInfo = { id: 456 };
        const suppliers: ISuppliers = { id: 6466 };
        supplierBanksInfo.suppliers = suppliers;

        activatedRoute.data = of({ supplierBanksInfo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(supplierBanksInfo));
        expect(comp.suppliersSharedCollection).toContain(suppliers);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SupplierBanksInfo>>();
        const supplierBanksInfo = { id: 123 };
        jest.spyOn(supplierBanksInfoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ supplierBanksInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: supplierBanksInfo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(supplierBanksInfoService.update).toHaveBeenCalledWith(supplierBanksInfo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SupplierBanksInfo>>();
        const supplierBanksInfo = new SupplierBanksInfo();
        jest.spyOn(supplierBanksInfoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ supplierBanksInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: supplierBanksInfo }));
        saveSubject.complete();

        // THEN
        expect(supplierBanksInfoService.create).toHaveBeenCalledWith(supplierBanksInfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SupplierBanksInfo>>();
        const supplierBanksInfo = { id: 123 };
        jest.spyOn(supplierBanksInfoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ supplierBanksInfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(supplierBanksInfoService.update).toHaveBeenCalledWith(supplierBanksInfo);
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
