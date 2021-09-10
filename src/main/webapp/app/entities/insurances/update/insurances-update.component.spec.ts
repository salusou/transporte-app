jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { InsurancesService } from '../service/insurances.service';
import { IInsurances, Insurances } from '../insurances.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';

import { InsurancesUpdateComponent } from './insurances-update.component';

describe('Component Tests', () => {
  describe('Insurances Management Update Component', () => {
    let comp: InsurancesUpdateComponent;
    let fixture: ComponentFixture<InsurancesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let insurancesService: InsurancesService;
    let affiliatesService: AffiliatesService;
    let stateProvincesService: StateProvincesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [InsurancesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(InsurancesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InsurancesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      insurancesService = TestBed.inject(InsurancesService);
      affiliatesService = TestBed.inject(AffiliatesService);
      stateProvincesService = TestBed.inject(StateProvincesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const insurances: IInsurances = { id: 456 };
        const affiliates: IAffiliates = { id: 10521 };
        insurances.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 34947 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ insurances });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call StateProvinces query and add missing value', () => {
        const insurances: IInsurances = { id: 456 };
        const toStateProvince: IStateProvinces = { id: 15405 };
        insurances.toStateProvince = toStateProvince;
        const forStateProvince: IStateProvinces = { id: 81615 };
        insurances.forStateProvince = forStateProvince;

        const stateProvincesCollection: IStateProvinces[] = [{ id: 14170 }];
        jest.spyOn(stateProvincesService, 'query').mockReturnValue(of(new HttpResponse({ body: stateProvincesCollection })));
        const additionalStateProvinces = [toStateProvince, forStateProvince];
        const expectedCollection: IStateProvinces[] = [...additionalStateProvinces, ...stateProvincesCollection];
        jest.spyOn(stateProvincesService, 'addStateProvincesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ insurances });
        comp.ngOnInit();

        expect(stateProvincesService.query).toHaveBeenCalled();
        expect(stateProvincesService.addStateProvincesToCollectionIfMissing).toHaveBeenCalledWith(
          stateProvincesCollection,
          ...additionalStateProvinces
        );
        expect(comp.stateProvincesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const insurances: IInsurances = { id: 456 };
        const affiliates: IAffiliates = { id: 35208 };
        insurances.affiliates = affiliates;
        const toStateProvince: IStateProvinces = { id: 38112 };
        insurances.toStateProvince = toStateProvince;
        const forStateProvince: IStateProvinces = { id: 49804 };
        insurances.forStateProvince = forStateProvince;

        activatedRoute.data = of({ insurances });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(insurances));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.stateProvincesSharedCollection).toContain(toStateProvince);
        expect(comp.stateProvincesSharedCollection).toContain(forStateProvince);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Insurances>>();
        const insurances = { id: 123 };
        jest.spyOn(insurancesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ insurances });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: insurances }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(insurancesService.update).toHaveBeenCalledWith(insurances);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Insurances>>();
        const insurances = new Insurances();
        jest.spyOn(insurancesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ insurances });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: insurances }));
        saveSubject.complete();

        // THEN
        expect(insurancesService.create).toHaveBeenCalledWith(insurances);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Insurances>>();
        const insurances = { id: 123 };
        jest.spyOn(insurancesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ insurances });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(insurancesService.update).toHaveBeenCalledWith(insurances);
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

      describe('trackStateProvincesById', () => {
        it('Should return tracked StateProvinces primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStateProvincesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
