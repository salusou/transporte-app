jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CompaniesService } from '../service/companies.service';
import { ICompanies, Companies } from '../companies.model';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';

import { CompaniesUpdateComponent } from './companies-update.component';

describe('Component Tests', () => {
  describe('Companies Management Update Component', () => {
    let comp: CompaniesUpdateComponent;
    let fixture: ComponentFixture<CompaniesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let companiesService: CompaniesService;
    let citiesService: CitiesService;
    let stateProvincesService: StateProvincesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CompaniesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CompaniesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompaniesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      companiesService = TestBed.inject(CompaniesService);
      citiesService = TestBed.inject(CitiesService);
      stateProvincesService = TestBed.inject(StateProvincesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Cities query and add missing value', () => {
        const companies: ICompanies = { id: 456 };
        const cities: ICities = { id: 87068 };
        companies.cities = cities;

        const citiesCollection: ICities[] = [{ id: 36256 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ companies });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call StateProvinces query and add missing value', () => {
        const companies: ICompanies = { id: 456 };
        const stateProvinces: IStateProvinces = { id: 91792 };
        companies.stateProvinces = stateProvinces;

        const stateProvincesCollection: IStateProvinces[] = [{ id: 16632 }];
        jest.spyOn(stateProvincesService, 'query').mockReturnValue(of(new HttpResponse({ body: stateProvincesCollection })));
        const additionalStateProvinces = [stateProvinces];
        const expectedCollection: IStateProvinces[] = [...additionalStateProvinces, ...stateProvincesCollection];
        jest.spyOn(stateProvincesService, 'addStateProvincesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ companies });
        comp.ngOnInit();

        expect(stateProvincesService.query).toHaveBeenCalled();
        expect(stateProvincesService.addStateProvincesToCollectionIfMissing).toHaveBeenCalledWith(
          stateProvincesCollection,
          ...additionalStateProvinces
        );
        expect(comp.stateProvincesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const companies: ICompanies = { id: 456 };
        const cities: ICities = { id: 92271 };
        companies.cities = cities;
        const stateProvinces: IStateProvinces = { id: 60592 };
        companies.stateProvinces = stateProvinces;

        activatedRoute.data = of({ companies });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(companies));
        expect(comp.citiesSharedCollection).toContain(cities);
        expect(comp.stateProvincesSharedCollection).toContain(stateProvinces);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Companies>>();
        const companies = { id: 123 };
        jest.spyOn(companiesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ companies });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: companies }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(companiesService.update).toHaveBeenCalledWith(companies);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Companies>>();
        const companies = new Companies();
        jest.spyOn(companiesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ companies });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: companies }));
        saveSubject.complete();

        // THEN
        expect(companiesService.create).toHaveBeenCalledWith(companies);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Companies>>();
        const companies = { id: 123 };
        jest.spyOn(companiesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ companies });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(companiesService.update).toHaveBeenCalledWith(companies);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCitiesById', () => {
        it('Should return tracked Cities primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCitiesById(0, entity);
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
