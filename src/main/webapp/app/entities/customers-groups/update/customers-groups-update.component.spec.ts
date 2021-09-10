jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomersGroupsService } from '../service/customers-groups.service';
import { ICustomersGroups, CustomersGroups } from '../customers-groups.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

import { CustomersGroupsUpdateComponent } from './customers-groups-update.component';

describe('Component Tests', () => {
  describe('CustomersGroups Management Update Component', () => {
    let comp: CustomersGroupsUpdateComponent;
    let fixture: ComponentFixture<CustomersGroupsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let customersGroupsService: CustomersGroupsService;
    let affiliatesService: AffiliatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomersGroupsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CustomersGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomersGroupsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      customersGroupsService = TestBed.inject(CustomersGroupsService);
      affiliatesService = TestBed.inject(AffiliatesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const customersGroups: ICustomersGroups = { id: 456 };
        const affiliates: IAffiliates = { id: 36302 };
        customersGroups.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 87450 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customersGroups });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const customersGroups: ICustomersGroups = { id: 456 };
        const affiliates: IAffiliates = { id: 7099 };
        customersGroups.affiliates = affiliates;

        activatedRoute.data = of({ customersGroups });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(customersGroups));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomersGroups>>();
        const customersGroups = { id: 123 };
        jest.spyOn(customersGroupsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customersGroups });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customersGroups }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(customersGroupsService.update).toHaveBeenCalledWith(customersGroups);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomersGroups>>();
        const customersGroups = new CustomersGroups();
        jest.spyOn(customersGroupsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customersGroups });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customersGroups }));
        saveSubject.complete();

        // THEN
        expect(customersGroupsService.create).toHaveBeenCalledWith(customersGroups);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CustomersGroups>>();
        const customersGroups = { id: 123 };
        jest.spyOn(customersGroupsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customersGroups });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(customersGroupsService.update).toHaveBeenCalledWith(customersGroups);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAffiliatesById', () => {
        it('Should return tracked Affiliates primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAffiliatesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
