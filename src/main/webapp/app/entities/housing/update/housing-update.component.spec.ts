jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HousingService } from '../service/housing.service';
import { IHousing, Housing } from '../housing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { IStatus } from 'app/entities/status/status.model';
import { StatusService } from 'app/entities/status/service/status.service';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';
import { IParking } from 'app/entities/parking/parking.model';
import { ParkingService } from 'app/entities/parking/service/parking.service';
import { ICostCenter } from 'app/entities/cost-center/cost-center.model';
import { CostCenterService } from 'app/entities/cost-center/service/cost-center.service';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

import { HousingUpdateComponent } from './housing-update.component';

describe('Component Tests', () => {
  describe('Housing Management Update Component', () => {
    let comp: HousingUpdateComponent;
    let fixture: ComponentFixture<HousingUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let housingService: HousingService;
    let affiliatesService: AffiliatesService;
    let statusService: StatusService;
    let customersService: CustomersService;
    let employeesService: EmployeesService;
    let parkingService: ParkingService;
    let costCenterService: CostCenterService;
    let suppliersService: SuppliersService;
    let citiesService: CitiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HousingUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HousingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HousingUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      housingService = TestBed.inject(HousingService);
      affiliatesService = TestBed.inject(AffiliatesService);
      statusService = TestBed.inject(StatusService);
      customersService = TestBed.inject(CustomersService);
      employeesService = TestBed.inject(EmployeesService);
      parkingService = TestBed.inject(ParkingService);
      costCenterService = TestBed.inject(CostCenterService);
      suppliersService = TestBed.inject(SuppliersService);
      citiesService = TestBed.inject(CitiesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const affiliates: IAffiliates = { id: 24034 };
        housing.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 85000 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Status query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const status: IStatus = { id: 97831 };
        housing.status = status;

        const statusCollection: IStatus[] = [{ id: 74888 }];
        jest.spyOn(statusService, 'query').mockReturnValue(of(new HttpResponse({ body: statusCollection })));
        const additionalStatuses = [status];
        const expectedCollection: IStatus[] = [...additionalStatuses, ...statusCollection];
        jest.spyOn(statusService, 'addStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(statusService.query).toHaveBeenCalled();
        expect(statusService.addStatusToCollectionIfMissing).toHaveBeenCalledWith(statusCollection, ...additionalStatuses);
        expect(comp.statusesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Customers query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const customers: ICustomers = { id: 18642 };
        housing.customers = customers;

        const customersCollection: ICustomers[] = [{ id: 13018 }];
        jest.spyOn(customersService, 'query').mockReturnValue(of(new HttpResponse({ body: customersCollection })));
        const additionalCustomers = [customers];
        const expectedCollection: ICustomers[] = [...additionalCustomers, ...customersCollection];
        jest.spyOn(customersService, 'addCustomersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(customersService.query).toHaveBeenCalled();
        expect(customersService.addCustomersToCollectionIfMissing).toHaveBeenCalledWith(customersCollection, ...additionalCustomers);
        expect(comp.customersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Employees query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const employees: IEmployees = { id: 73586 };
        housing.employees = employees;

        const employeesCollection: IEmployees[] = [{ id: 95091 }];
        jest.spyOn(employeesService, 'query').mockReturnValue(of(new HttpResponse({ body: employeesCollection })));
        const additionalEmployees = [employees];
        const expectedCollection: IEmployees[] = [...additionalEmployees, ...employeesCollection];
        jest.spyOn(employeesService, 'addEmployeesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(employeesService.query).toHaveBeenCalled();
        expect(employeesService.addEmployeesToCollectionIfMissing).toHaveBeenCalledWith(employeesCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Parking query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const parking: IParking = { id: 94733 };
        housing.parking = parking;

        const parkingCollection: IParking[] = [{ id: 25566 }];
        jest.spyOn(parkingService, 'query').mockReturnValue(of(new HttpResponse({ body: parkingCollection })));
        const additionalParkings = [parking];
        const expectedCollection: IParking[] = [...additionalParkings, ...parkingCollection];
        jest.spyOn(parkingService, 'addParkingToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(parkingService.query).toHaveBeenCalled();
        expect(parkingService.addParkingToCollectionIfMissing).toHaveBeenCalledWith(parkingCollection, ...additionalParkings);
        expect(comp.parkingsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CostCenter query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const costCenter: ICostCenter = { id: 47471 };
        housing.costCenter = costCenter;

        const costCenterCollection: ICostCenter[] = [{ id: 9107 }];
        jest.spyOn(costCenterService, 'query').mockReturnValue(of(new HttpResponse({ body: costCenterCollection })));
        const additionalCostCenters = [costCenter];
        const expectedCollection: ICostCenter[] = [...additionalCostCenters, ...costCenterCollection];
        jest.spyOn(costCenterService, 'addCostCenterToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(costCenterService.query).toHaveBeenCalled();
        expect(costCenterService.addCostCenterToCollectionIfMissing).toHaveBeenCalledWith(costCenterCollection, ...additionalCostCenters);
        expect(comp.costCentersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Suppliers query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const suppliers: ISuppliers = { id: 84104 };
        housing.suppliers = suppliers;

        const suppliersCollection: ISuppliers[] = [{ id: 14730 }];
        jest.spyOn(suppliersService, 'query').mockReturnValue(of(new HttpResponse({ body: suppliersCollection })));
        const additionalSuppliers = [suppliers];
        const expectedCollection: ISuppliers[] = [...additionalSuppliers, ...suppliersCollection];
        jest.spyOn(suppliersService, 'addSuppliersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(suppliersService.query).toHaveBeenCalled();
        expect(suppliersService.addSuppliersToCollectionIfMissing).toHaveBeenCalledWith(suppliersCollection, ...additionalSuppliers);
        expect(comp.suppliersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const housing: IHousing = { id: 456 };
        const cities: ICities = { id: 97170 };
        housing.cities = cities;

        const citiesCollection: ICities[] = [{ id: 41931 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const housing: IHousing = { id: 456 };
        const affiliates: IAffiliates = { id: 14338 };
        housing.affiliates = affiliates;
        const status: IStatus = { id: 87501 };
        housing.status = status;
        const customers: ICustomers = { id: 2976 };
        housing.customers = customers;
        const employees: IEmployees = { id: 20813 };
        housing.employees = employees;
        const parking: IParking = { id: 29602 };
        housing.parking = parking;
        const costCenter: ICostCenter = { id: 89985 };
        housing.costCenter = costCenter;
        const suppliers: ISuppliers = { id: 19741 };
        housing.suppliers = suppliers;
        const cities: ICities = { id: 6806 };
        housing.cities = cities;

        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(housing));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.statusesSharedCollection).toContain(status);
        expect(comp.customersSharedCollection).toContain(customers);
        expect(comp.employeesSharedCollection).toContain(employees);
        expect(comp.parkingsSharedCollection).toContain(parking);
        expect(comp.costCentersSharedCollection).toContain(costCenter);
        expect(comp.suppliersSharedCollection).toContain(suppliers);
        expect(comp.citiesSharedCollection).toContain(cities);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Housing>>();
        const housing = { id: 123 };
        jest.spyOn(housingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: housing }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(housingService.update).toHaveBeenCalledWith(housing);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Housing>>();
        const housing = new Housing();
        jest.spyOn(housingService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: housing }));
        saveSubject.complete();

        // THEN
        expect(housingService.create).toHaveBeenCalledWith(housing);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Housing>>();
        const housing = { id: 123 };
        jest.spyOn(housingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ housing });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(housingService.update).toHaveBeenCalledWith(housing);
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

      describe('trackStatusById', () => {
        it('Should return tracked Status primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStatusById(0, entity);
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

      describe('trackEmployeesById', () => {
        it('Should return tracked Employees primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmployeesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackParkingById', () => {
        it('Should return tracked Parking primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackParkingById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCostCenterById', () => {
        it('Should return tracked CostCenter primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCostCenterById(0, entity);
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
    });
  });
});
