jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleLocationStatusService } from '../service/vehicle-location-status.service';
import { IVehicleLocationStatus, VehicleLocationStatus } from '../vehicle-location-status.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

import { VehicleLocationStatusUpdateComponent } from './vehicle-location-status-update.component';

describe('Component Tests', () => {
  describe('VehicleLocationStatus Management Update Component', () => {
    let comp: VehicleLocationStatusUpdateComponent;
    let fixture: ComponentFixture<VehicleLocationStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleLocationStatusService: VehicleLocationStatusService;
    let vehicleControlsService: VehicleControlsService;
    let citiesService: CitiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleLocationStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleLocationStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleLocationStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleLocationStatusService = TestBed.inject(VehicleLocationStatusService);
      vehicleControlsService = TestBed.inject(VehicleControlsService);
      citiesService = TestBed.inject(CitiesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControls query and add missing value', () => {
        const vehicleLocationStatus: IVehicleLocationStatus = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 85149 };
        vehicleLocationStatus.vehicleControls = vehicleControls;

        const vehicleControlsCollection: IVehicleControls[] = [{ id: 70190 }];
        jest.spyOn(vehicleControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlsCollection })));
        const additionalVehicleControls = [vehicleControls];
        const expectedCollection: IVehicleControls[] = [...additionalVehicleControls, ...vehicleControlsCollection];
        jest.spyOn(vehicleControlsService, 'addVehicleControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleLocationStatus });
        comp.ngOnInit();

        expect(vehicleControlsService.query).toHaveBeenCalled();
        expect(vehicleControlsService.addVehicleControlsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlsCollection,
          ...additionalVehicleControls
        );
        expect(comp.vehicleControlsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const vehicleLocationStatus: IVehicleLocationStatus = { id: 456 };
        const cities: ICities = { id: 48045 };
        vehicleLocationStatus.cities = cities;

        const citiesCollection: ICities[] = [{ id: 7360 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleLocationStatus });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleLocationStatus: IVehicleLocationStatus = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 88689 };
        vehicleLocationStatus.vehicleControls = vehicleControls;
        const cities: ICities = { id: 93731 };
        vehicleLocationStatus.cities = cities;

        activatedRoute.data = of({ vehicleLocationStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleLocationStatus));
        expect(comp.vehicleControlsSharedCollection).toContain(vehicleControls);
        expect(comp.citiesSharedCollection).toContain(cities);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleLocationStatus>>();
        const vehicleLocationStatus = { id: 123 };
        jest.spyOn(vehicleLocationStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleLocationStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleLocationStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleLocationStatusService.update).toHaveBeenCalledWith(vehicleLocationStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleLocationStatus>>();
        const vehicleLocationStatus = new VehicleLocationStatus();
        jest.spyOn(vehicleLocationStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleLocationStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleLocationStatus }));
        saveSubject.complete();

        // THEN
        expect(vehicleLocationStatusService.create).toHaveBeenCalledWith(vehicleLocationStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleLocationStatus>>();
        const vehicleLocationStatus = { id: 123 };
        jest.spyOn(vehicleLocationStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleLocationStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleLocationStatusService.update).toHaveBeenCalledWith(vehicleLocationStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVehicleControlsById', () => {
        it('Should return tracked VehicleControls primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVehicleControlsById(0, entity);
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
    });
  });
});
