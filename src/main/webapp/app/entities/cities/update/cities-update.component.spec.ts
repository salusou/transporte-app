jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CitiesService } from '../service/cities.service';
import { ICities, Cities } from '../cities.model';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';

import { CitiesUpdateComponent } from './cities-update.component';

describe('Component Tests', () => {
  describe('Cities Management Update Component', () => {
    let comp: CitiesUpdateComponent;
    let fixture: ComponentFixture<CitiesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let citiesService: CitiesService;
    let stateProvincesService: StateProvincesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CitiesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CitiesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CitiesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      citiesService = TestBed.inject(CitiesService);
      stateProvincesService = TestBed.inject(StateProvincesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call StateProvinces query and add missing value', () => {
        const cities: ICities = { id: 456 };
        const stateProvinces: IStateProvinces = { id: 7262 };
        cities.stateProvinces = stateProvinces;

        const stateProvincesCollection: IStateProvinces[] = [{ id: 30602 }];
        jest.spyOn(stateProvincesService, 'query').mockReturnValue(of(new HttpResponse({ body: stateProvincesCollection })));
        const additionalStateProvinces = [stateProvinces];
        const expectedCollection: IStateProvinces[] = [...additionalStateProvinces, ...stateProvincesCollection];
        jest.spyOn(stateProvincesService, 'addStateProvincesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ cities });
        comp.ngOnInit();

        expect(stateProvincesService.query).toHaveBeenCalled();
        expect(stateProvincesService.addStateProvincesToCollectionIfMissing).toHaveBeenCalledWith(
          stateProvincesCollection,
          ...additionalStateProvinces
        );
        expect(comp.stateProvincesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const cities: ICities = { id: 456 };
        const stateProvinces: IStateProvinces = { id: 456 };
        cities.stateProvinces = stateProvinces;

        activatedRoute.data = of({ cities });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cities));
        expect(comp.stateProvincesSharedCollection).toContain(stateProvinces);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Cities>>();
        const cities = { id: 123 };
        jest.spyOn(citiesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cities });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cities }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(citiesService.update).toHaveBeenCalledWith(cities);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Cities>>();
        const cities = new Cities();
        jest.spyOn(citiesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cities });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cities }));
        saveSubject.complete();

        // THEN
        expect(citiesService.create).toHaveBeenCalledWith(cities);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Cities>>();
        const cities = { id: 123 };
        jest.spyOn(citiesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cities });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(citiesService.update).toHaveBeenCalledWith(cities);
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
    });
  });
});
