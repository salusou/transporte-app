jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleControlsService } from '../service/vehicle-controls.service';
import { IVehicleControls, VehicleControls } from '../vehicle-controls.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';
import { CustomersGroupsService } from 'app/entities/customers-groups/service/customers-groups.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IStatus } from 'app/entities/status/status.model';
import { StatusService } from 'app/entities/status/service/status.service';

import { VehicleControlsUpdateComponent } from './vehicle-controls-update.component';

describe('Component Tests', () => {
  describe('VehicleControls Management Update Component', () => {
    let comp: VehicleControlsUpdateComponent;
    let fixture: ComponentFixture<VehicleControlsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleControlsService: VehicleControlsService;
    let affiliatesService: AffiliatesService;
    let customersService: CustomersService;
    let customersGroupsService: CustomersGroupsService;
    let employeesService: EmployeesService;
    let citiesService: CitiesService;
    let statusService: StatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleControlsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleControlsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleControlsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleControlsService = TestBed.inject(VehicleControlsService);
      affiliatesService = TestBed.inject(AffiliatesService);
      customersService = TestBed.inject(CustomersService);
      customersGroupsService = TestBed.inject(CustomersGroupsService);
      employeesService = TestBed.inject(EmployeesService);
      citiesService = TestBed.inject(CitiesService);
      statusService = TestBed.inject(StatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const affiliates: IAffiliates = { id: 64639 };
        vehicleControls.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 30366 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Customers query and add missing value', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const customers: ICustomers = { id: 51081 };
        vehicleControls.customers = customers;

        const customersCollection: ICustomers[] = [{ id: 19110 }];
        jest.spyOn(customersService, 'query').mockReturnValue(of(new HttpResponse({ body: customersCollection })));
        const additionalCustomers = [customers];
        const expectedCollection: ICustomers[] = [...additionalCustomers, ...customersCollection];
        jest.spyOn(customersService, 'addCustomersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(customersService.query).toHaveBeenCalled();
        expect(customersService.addCustomersToCollectionIfMissing).toHaveBeenCalledWith(customersCollection, ...additionalCustomers);
        expect(comp.customersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CustomersGroups query and add missing value', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const customersGroups: ICustomersGroups = { id: 98717 };
        vehicleControls.customersGroups = customersGroups;

        const customersGroupsCollection: ICustomersGroups[] = [{ id: 49705 }];
        jest.spyOn(customersGroupsService, 'query').mockReturnValue(of(new HttpResponse({ body: customersGroupsCollection })));
        const additionalCustomersGroups = [customersGroups];
        const expectedCollection: ICustomersGroups[] = [...additionalCustomersGroups, ...customersGroupsCollection];
        jest.spyOn(customersGroupsService, 'addCustomersGroupsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(customersGroupsService.query).toHaveBeenCalled();
        expect(customersGroupsService.addCustomersGroupsToCollectionIfMissing).toHaveBeenCalledWith(
          customersGroupsCollection,
          ...additionalCustomersGroups
        );
        expect(comp.customersGroupsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Employees query and add missing value', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const employees: IEmployees = { id: 1609 };
        vehicleControls.employees = employees;

        const employeesCollection: IEmployees[] = [{ id: 551 }];
        jest.spyOn(employeesService, 'query').mockReturnValue(of(new HttpResponse({ body: employeesCollection })));
        const additionalEmployees = [employees];
        const expectedCollection: IEmployees[] = [...additionalEmployees, ...employeesCollection];
        jest.spyOn(employeesService, 'addEmployeesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(employeesService.query).toHaveBeenCalled();
        expect(employeesService.addEmployeesToCollectionIfMissing).toHaveBeenCalledWith(employeesCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const origin: ICities = { id: 72366 };
        vehicleControls.origin = origin;
        const destination: ICities = { id: 50801 };
        vehicleControls.destination = destination;

        const citiesCollection: ICities[] = [{ id: 67213 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [origin, destination];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Status query and add missing value', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const status: IStatus = { id: 35778 };
        vehicleControls.status = status;

        const statusCollection: IStatus[] = [{ id: 37849 }];
        jest.spyOn(statusService, 'query').mockReturnValue(of(new HttpResponse({ body: statusCollection })));
        const additionalStatuses = [status];
        const expectedCollection: IStatus[] = [...additionalStatuses, ...statusCollection];
        jest.spyOn(statusService, 'addStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(statusService.query).toHaveBeenCalled();
        expect(statusService.addStatusToCollectionIfMissing).toHaveBeenCalledWith(statusCollection, ...additionalStatuses);
        expect(comp.statusesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleControls: IVehicleControls = { id: 456 };
        const affiliates: IAffiliates = { id: 33387 };
        vehicleControls.affiliates = affiliates;
        const customers: ICustomers = { id: 72567 };
        vehicleControls.customers = customers;
        const customersGroups: ICustomersGroups = { id: 37428 };
        vehicleControls.customersGroups = customersGroups;
        const employees: IEmployees = { id: 33693 };
        vehicleControls.employees = employees;
        const origin: ICities = { id: 58556 };
        vehicleControls.origin = origin;
        const destination: ICities = { id: 82235 };
        vehicleControls.destination = destination;
        const status: IStatus = { id: 5730 };
        vehicleControls.status = status;

        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleControls));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.customersSharedCollection).toContain(customers);
        expect(comp.customersGroupsSharedCollection).toContain(customersGroups);
        expect(comp.employeesSharedCollection).toContain(employees);
        expect(comp.citiesSharedCollection).toContain(origin);
        expect(comp.citiesSharedCollection).toContain(destination);
        expect(comp.statusesSharedCollection).toContain(status);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControls>>();
        const vehicleControls = { id: 123 };
        jest.spyOn(vehicleControlsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControls }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleControlsService.update).toHaveBeenCalledWith(vehicleControls);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControls>>();
        const vehicleControls = new VehicleControls();
        jest.spyOn(vehicleControlsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControls }));
        saveSubject.complete();

        // THEN
        expect(vehicleControlsService.create).toHaveBeenCalledWith(vehicleControls);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControls>>();
        const vehicleControls = { id: 123 };
        jest.spyOn(vehicleControlsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControls });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleControlsService.update).toHaveBeenCalledWith(vehicleControls);
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

      describe('trackCustomersById', () => {
        it('Should return tracked Customers primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCustomersById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCustomersGroupsById', () => {
        it('Should return tracked CustomersGroups primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCustomersGroupsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEmployeesById', () => {
        it('Should return tracked Employees primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmployeesById(0, entity);
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

      describe('trackStatusById', () => {
        it('Should return tracked Status primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStatusById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
