jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleControlExpensesService } from '../service/vehicle-control-expenses.service';
import { IVehicleControlExpenses, VehicleControlExpenses } from '../vehicle-control-expenses.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { VehicleControlItemService } from 'app/entities/vehicle-control-item/service/vehicle-control-item.service';

import { VehicleControlExpensesUpdateComponent } from './vehicle-control-expenses-update.component';

describe('Component Tests', () => {
  describe('VehicleControlExpenses Management Update Component', () => {
    let comp: VehicleControlExpensesUpdateComponent;
    let fixture: ComponentFixture<VehicleControlExpensesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleControlExpensesService: VehicleControlExpensesService;
    let vehicleControlsService: VehicleControlsService;
    let suppliersService: SuppliersService;
    let citiesService: CitiesService;
    let vehicleControlItemService: VehicleControlItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleControlExpensesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleControlExpensesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleControlExpensesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleControlExpensesService = TestBed.inject(VehicleControlExpensesService);
      vehicleControlsService = TestBed.inject(VehicleControlsService);
      suppliersService = TestBed.inject(SuppliersService);
      citiesService = TestBed.inject(CitiesService);
      vehicleControlItemService = TestBed.inject(VehicleControlItemService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControls query and add missing value', () => {
        const vehicleControlExpenses: IVehicleControlExpenses = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 29261 };
        vehicleControlExpenses.vehicleControls = vehicleControls;

        const vehicleControlsCollection: IVehicleControls[] = [{ id: 67471 }];
        jest.spyOn(vehicleControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlsCollection })));
        const additionalVehicleControls = [vehicleControls];
        const expectedCollection: IVehicleControls[] = [...additionalVehicleControls, ...vehicleControlsCollection];
        jest.spyOn(vehicleControlsService, 'addVehicleControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        expect(vehicleControlsService.query).toHaveBeenCalled();
        expect(vehicleControlsService.addVehicleControlsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlsCollection,
          ...additionalVehicleControls
        );
        expect(comp.vehicleControlsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Suppliers query and add missing value', () => {
        const vehicleControlExpenses: IVehicleControlExpenses = { id: 456 };
        const suppliers: ISuppliers = { id: 4614 };
        vehicleControlExpenses.suppliers = suppliers;

        const suppliersCollection: ISuppliers[] = [{ id: 92044 }];
        jest.spyOn(suppliersService, 'query').mockReturnValue(of(new HttpResponse({ body: suppliersCollection })));
        const additionalSuppliers = [suppliers];
        const expectedCollection: ISuppliers[] = [...additionalSuppliers, ...suppliersCollection];
        jest.spyOn(suppliersService, 'addSuppliersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        expect(suppliersService.query).toHaveBeenCalled();
        expect(suppliersService.addSuppliersToCollectionIfMissing).toHaveBeenCalledWith(suppliersCollection, ...additionalSuppliers);
        expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const vehicleControlExpenses: IVehicleControlExpenses = { id: 456 };
        const origin: ICities = { id: 80277 };
        vehicleControlExpenses.origin = origin;
        const destination: ICities = { id: 25799 };
        vehicleControlExpenses.destination = destination;

        const citiesCollection: ICities[] = [{ id: 27922 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [origin, destination];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call VehicleControlItem query and add missing value', () => {
        const vehicleControlExpenses: IVehicleControlExpenses = { id: 456 };
        const vehicleControlItem: IVehicleControlItem = { id: 6628 };
        vehicleControlExpenses.vehicleControlItem = vehicleControlItem;

        const vehicleControlItemCollection: IVehicleControlItem[] = [{ id: 45464 }];
        jest.spyOn(vehicleControlItemService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlItemCollection })));
        const additionalVehicleControlItems = [vehicleControlItem];
        const expectedCollection: IVehicleControlItem[] = [...additionalVehicleControlItems, ...vehicleControlItemCollection];
        jest.spyOn(vehicleControlItemService, 'addVehicleControlItemToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        expect(vehicleControlItemService.query).toHaveBeenCalled();
        expect(vehicleControlItemService.addVehicleControlItemToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlItemCollection,
          ...additionalVehicleControlItems
        );
        expect(comp.vehicleControlItemsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleControlExpenses: IVehicleControlExpenses = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 787 };
        vehicleControlExpenses.vehicleControls = vehicleControls;
        const suppliers: ISuppliers = { id: 47652 };
        vehicleControlExpenses.suppliers = suppliers;
        const origin: ICities = { id: 53352 };
        vehicleControlExpenses.origin = origin;
        const destination: ICities = { id: 74627 };
        vehicleControlExpenses.destination = destination;
        const vehicleControlItem: IVehicleControlItem = { id: 7020 };
        vehicleControlExpenses.vehicleControlItem = vehicleControlItem;

        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleControlExpenses));
        expect(comp.vehicleControlsSharedCollection).toContain(vehicleControls);
        expect(comp.suppliersSharedCollection).toContain(suppliers);
        expect(comp.citiesSharedCollection).toContain(origin);
        expect(comp.citiesSharedCollection).toContain(destination);
        expect(comp.vehicleControlItemsSharedCollection).toContain(vehicleControlItem);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlExpenses>>();
        const vehicleControlExpenses = { id: 123 };
        jest.spyOn(vehicleControlExpensesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlExpenses }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleControlExpensesService.update).toHaveBeenCalledWith(vehicleControlExpenses);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlExpenses>>();
        const vehicleControlExpenses = new VehicleControlExpenses();
        jest.spyOn(vehicleControlExpensesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlExpenses }));
        saveSubject.complete();

        // THEN
        expect(vehicleControlExpensesService.create).toHaveBeenCalledWith(vehicleControlExpenses);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlExpenses>>();
        const vehicleControlExpenses = { id: 123 };
        jest.spyOn(vehicleControlExpensesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlExpenses });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleControlExpensesService.update).toHaveBeenCalledWith(vehicleControlExpenses);
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

      describe('trackSuppliersById', () => {
        it('Should return tracked Suppliers primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSuppliersById(0, entity);
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

      describe('trackVehicleControlItemById', () => {
        it('Should return tracked VehicleControlItem primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVehicleControlItemById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
