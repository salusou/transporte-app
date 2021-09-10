jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StateProvincesService } from '../service/state-provinces.service';
import { IStateProvinces, StateProvinces } from '../state-provinces.model';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

import { StateProvincesUpdateComponent } from './state-provinces-update.component';

describe('Component Tests', () => {
  describe('StateProvinces Management Update Component', () => {
    let comp: StateProvincesUpdateComponent;
    let fixture: ComponentFixture<StateProvincesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let stateProvincesService: StateProvincesService;
    let countriesService: CountriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StateProvincesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(StateProvincesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateProvincesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      stateProvincesService = TestBed.inject(StateProvincesService);
      countriesService = TestBed.inject(CountriesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Countries query and add missing value', () => {
        const stateProvinces: IStateProvinces = { id: 456 };
        const countries: ICountries = { id: 48161 };
        stateProvinces.countries = countries;

        const countriesCollection: ICountries[] = [{ id: 1741 }];
        jest.spyOn(countriesService, 'query').mockReturnValue(of(new HttpResponse({ body: countriesCollection })));
        const additionalCountries = [countries];
        const expectedCollection: ICountries[] = [...additionalCountries, ...countriesCollection];
        jest.spyOn(countriesService, 'addCountriesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ stateProvinces });
        comp.ngOnInit();

        expect(countriesService.query).toHaveBeenCalled();
        expect(countriesService.addCountriesToCollectionIfMissing).toHaveBeenCalledWith(countriesCollection, ...additionalCountries);
        expect(comp.countriesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const stateProvinces: IStateProvinces = { id: 456 };
        const countries: ICountries = { id: 16481 };
        stateProvinces.countries = countries;

        activatedRoute.data = of({ stateProvinces });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(stateProvinces));
        expect(comp.countriesSharedCollection).toContain(countries);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StateProvinces>>();
        const stateProvinces = { id: 123 };
        jest.spyOn(stateProvincesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ stateProvinces });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: stateProvinces }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(stateProvincesService.update).toHaveBeenCalledWith(stateProvinces);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StateProvinces>>();
        const stateProvinces = new StateProvinces();
        jest.spyOn(stateProvincesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ stateProvinces });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: stateProvinces }));
        saveSubject.complete();

        // THEN
        expect(stateProvincesService.create).toHaveBeenCalledWith(stateProvinces);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StateProvinces>>();
        const stateProvinces = { id: 123 };
        jest.spyOn(stateProvincesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ stateProvinces });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(stateProvincesService.update).toHaveBeenCalledWith(stateProvinces);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCountriesById', () => {
        it('Should return tracked Countries primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCountriesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
