jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmployeesService } from '../service/employees.service';
import { IEmployees, Employees } from '../employees.model';
import { ICompanies } from 'app/entities/companies/companies.model';
import { CompaniesService } from 'app/entities/companies/service/companies.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IPositions } from 'app/entities/positions/positions.model';
import { PositionsService } from 'app/entities/positions/service/positions.service';

import { EmployeesUpdateComponent } from './employees-update.component';

describe('Component Tests', () => {
  describe('Employees Management Update Component', () => {
    let comp: EmployeesUpdateComponent;
    let fixture: ComponentFixture<EmployeesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let employeesService: EmployeesService;
    let companiesService: CompaniesService;
    let affiliatesService: AffiliatesService;
    let citiesService: CitiesService;
    let positionsService: PositionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EmployeesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      employeesService = TestBed.inject(EmployeesService);
      companiesService = TestBed.inject(CompaniesService);
      affiliatesService = TestBed.inject(AffiliatesService);
      citiesService = TestBed.inject(CitiesService);
      positionsService = TestBed.inject(PositionsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Companies query and add missing value', () => {
        const employees: IEmployees = { id: 456 };
        const companies: ICompanies = { id: 36897 };
        employees.companies = companies;

        const companiesCollection: ICompanies[] = [{ id: 34737 }];
        jest.spyOn(companiesService, 'query').mockReturnValue(of(new HttpResponse({ body: companiesCollection })));
        const additionalCompanies = [companies];
        const expectedCollection: ICompanies[] = [...additionalCompanies, ...companiesCollection];
        jest.spyOn(companiesService, 'addCompaniesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        expect(companiesService.query).toHaveBeenCalled();
        expect(companiesService.addCompaniesToCollectionIfMissing).toHaveBeenCalledWith(companiesCollection, ...additionalCompanies);
        expect(comp.companiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Affiliates query and add missing value', () => {
        const employees: IEmployees = { id: 456 };
        const affiliates: IAffiliates = { id: 34555 };
        employees.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 14287 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const employees: IEmployees = { id: 456 };
        const cities: ICities = { id: 37939 };
        employees.cities = cities;

        const citiesCollection: ICities[] = [{ id: 26878 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Positions query and add missing value', () => {
        const employees: IEmployees = { id: 456 };
        const positions: IPositions = { id: 97356 };
        employees.positions = positions;

        const positionsCollection: IPositions[] = [{ id: 3732 }];
        jest.spyOn(positionsService, 'query').mockReturnValue(of(new HttpResponse({ body: positionsCollection })));
        const additionalPositions = [positions];
        const expectedCollection: IPositions[] = [...additionalPositions, ...positionsCollection];
        jest.spyOn(positionsService, 'addPositionsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        expect(positionsService.query).toHaveBeenCalled();
        expect(positionsService.addPositionsToCollectionIfMissing).toHaveBeenCalledWith(positionsCollection, ...additionalPositions);
        expect(comp.positionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const employees: IEmployees = { id: 456 };
        const companies: ICompanies = { id: 71993 };
        employees.companies = companies;
        const affiliates: IAffiliates = { id: 8619 };
        employees.affiliates = affiliates;
        const cities: ICities = { id: 61502 };
        employees.cities = cities;
        const positions: IPositions = { id: 65484 };
        employees.positions = positions;

        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(employees));
        expect(comp.companiesSharedCollection).toContain(companies);
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.citiesSharedCollection).toContain(cities);
        expect(comp.positionsSharedCollection).toContain(positions);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Employees>>();
        const employees = { id: 123 };
        jest.spyOn(employeesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employees }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(employeesService.update).toHaveBeenCalledWith(employees);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Employees>>();
        const employees = new Employees();
        jest.spyOn(employeesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employees }));
        saveSubject.complete();

        // THEN
        expect(employeesService.create).toHaveBeenCalledWith(employees);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Employees>>();
        const employees = { id: 123 };
        jest.spyOn(employeesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employees });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(employeesService.update).toHaveBeenCalledWith(employees);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCompaniesById', () => {
        it('Should return tracked Companies primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCompaniesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAffiliatesById', () => {
        it('Should return tracked Affiliates primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAffiliatesById(0, entity);
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

      describe('trackPositionsById', () => {
        it('Should return tracked Positions primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPositionsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
