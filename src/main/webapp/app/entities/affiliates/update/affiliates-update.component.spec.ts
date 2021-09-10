jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AffiliatesService } from '../service/affiliates.service';
import { IAffiliates, Affiliates } from '../affiliates.model';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { ICompanies } from 'app/entities/companies/companies.model';
import { CompaniesService } from 'app/entities/companies/service/companies.service';

import { AffiliatesUpdateComponent } from './affiliates-update.component';

describe('Component Tests', () => {
  describe('Affiliates Management Update Component', () => {
    let comp: AffiliatesUpdateComponent;
    let fixture: ComponentFixture<AffiliatesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let affiliatesService: AffiliatesService;
    let stateProvincesService: StateProvincesService;
    let citiesService: CitiesService;
    let companiesService: CompaniesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AffiliatesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AffiliatesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AffiliatesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      affiliatesService = TestBed.inject(AffiliatesService);
      stateProvincesService = TestBed.inject(StateProvincesService);
      citiesService = TestBed.inject(CitiesService);
      companiesService = TestBed.inject(CompaniesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call StateProvinces query and add missing value', () => {
        const affiliates: IAffiliates = { id: 456 };
        const stateProvinces: IStateProvinces = { id: 10168 };
        affiliates.stateProvinces = stateProvinces;

        const stateProvincesCollection: IStateProvinces[] = [{ id: 19378 }];
        jest.spyOn(stateProvincesService, 'query').mockReturnValue(of(new HttpResponse({ body: stateProvincesCollection })));
        const additionalStateProvinces = [stateProvinces];
        const expectedCollection: IStateProvinces[] = [...additionalStateProvinces, ...stateProvincesCollection];
        jest.spyOn(stateProvincesService, 'addStateProvincesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        expect(stateProvincesService.query).toHaveBeenCalled();
        expect(stateProvincesService.addStateProvincesToCollectionIfMissing).toHaveBeenCalledWith(
          stateProvincesCollection,
          ...additionalStateProvinces
        );
        expect(comp.stateProvincesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const affiliates: IAffiliates = { id: 456 };
        const cities: ICities = { id: 48968 };
        affiliates.cities = cities;

        const citiesCollection: ICities[] = [{ id: 97913 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Companies query and add missing value', () => {
        const affiliates: IAffiliates = { id: 456 };
        const companies: ICompanies = { id: 29941 };
        affiliates.companies = companies;

        const companiesCollection: ICompanies[] = [{ id: 19915 }];
        jest.spyOn(companiesService, 'query').mockReturnValue(of(new HttpResponse({ body: companiesCollection })));
        const additionalCompanies = [companies];
        const expectedCollection: ICompanies[] = [...additionalCompanies, ...companiesCollection];
        jest.spyOn(companiesService, 'addCompaniesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        expect(companiesService.query).toHaveBeenCalled();
        expect(companiesService.addCompaniesToCollectionIfMissing).toHaveBeenCalledWith(companiesCollection, ...additionalCompanies);
        expect(comp.companiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const affiliates: IAffiliates = { id: 456 };
        const stateProvinces: IStateProvinces = { id: 18384 };
        affiliates.stateProvinces = stateProvinces;
        const cities: ICities = { id: 49101 };
        affiliates.cities = cities;
        const companies: ICompanies = { id: 28187 };
        affiliates.companies = companies;

        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(affiliates));
        expect(comp.stateProvincesSharedCollection).toContain(stateProvinces);
        expect(comp.citiesSharedCollection).toContain(cities);
        expect(comp.companiesSharedCollection).toContain(companies);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Affiliates>>();
        const affiliates = { id: 123 };
        jest.spyOn(affiliatesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: affiliates }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(affiliatesService.update).toHaveBeenCalledWith(affiliates);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Affiliates>>();
        const affiliates = new Affiliates();
        jest.spyOn(affiliatesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: affiliates }));
        saveSubject.complete();

        // THEN
        expect(affiliatesService.create).toHaveBeenCalledWith(affiliates);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Affiliates>>();
        const affiliates = { id: 123 };
        jest.spyOn(affiliatesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ affiliates });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(affiliatesService.update).toHaveBeenCalledWith(affiliates);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackStateProvincesById', () => {
        it('Should return tracked StateProvinces primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStateProvincesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCitiesById', () => {
        it('Should return tracked Cities primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCitiesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCompaniesById', () => {
        it('Should return tracked Companies primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCompaniesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
