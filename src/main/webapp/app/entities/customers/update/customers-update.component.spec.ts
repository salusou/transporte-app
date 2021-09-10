jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomersService } from '../service/customers.service';
import { ICustomers, Customers } from '../customers.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';
import { CustomersGroupsService } from 'app/entities/customers-groups/service/customers-groups.service';

import { CustomersUpdateComponent } from './customers-update.component';

describe('Component Tests', () => {
  describe('Customers Management Update Component', () => {
    let comp: CustomersUpdateComponent;
    let fixture: ComponentFixture<CustomersUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let customersService: CustomersService;
    let affiliatesService: AffiliatesService;
    let citiesService: CitiesService;
    let customersGroupsService: CustomersGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomersUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CustomersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomersUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      customersService = TestBed.inject(CustomersService);
      affiliatesService = TestBed.inject(AffiliatesService);
      citiesService = TestBed.inject(CitiesService);
      customersGroupsService = TestBed.inject(CustomersGroupsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const customers: ICustomers = { id: 456 };
        const affiliates: IAffiliates = { id: 34255 };
        customers.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 42050 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const customers: ICustomers = { id: 456 };
        const cities: ICities = { id: 77785 };
        customers.cities = cities;

        const citiesCollection: ICities[] = [{ id: 46752 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CustomersGroups query and add missing value', () => {
        const customers: ICustomers = { id: 456 };
        const customersGroups: ICustomersGroups = { id: 23667 };
        customers.customersGroups = customersGroups;

        const customersGroupsCollection: ICustomersGroups[] = [{ id: 17114 }];
        jest.spyOn(customersGroupsService, 'query').mockReturnValue(of(new HttpResponse({ body: customersGroupsCollection })));
        const additionalCustomersGroups = [customersGroups];
        const expectedCollection: ICustomersGroups[] = [...additionalCustomersGroups, ...customersGroupsCollection];
        jest.spyOn(customersGroupsService, 'addCustomersGroupsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        expect(customersGroupsService.query).toHaveBeenCalled();
        expect(customersGroupsService.addCustomersGroupsToCollectionIfMissing).toHaveBeenCalledWith(
          customersGroupsCollection,
          ...additionalCustomersGroups
        );
        expect(comp.customersGroupsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const customers: ICustomers = { id: 456 };
        const affiliates: IAffiliates = { id: 11189 };
        customers.affiliates = affiliates;
        const cities: ICities = { id: 84170 };
        customers.cities = cities;
        const customersGroups: ICustomersGroups = { id: 97673 };
        customers.customersGroups = customersGroups;

        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(customers));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.citiesSharedCollection).toContain(cities);
        expect(comp.customersGroupsSharedCollection).toContain(customersGroups);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Customers>>();
        const customers = { id: 123 };
        jest.spyOn(customersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customers }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(customersService.update).toHaveBeenCalledWith(customers);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Customers>>();
        const customers = new Customers();
        jest.spyOn(customersService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customers }));
        saveSubject.complete();

        // THEN
        expect(customersService.create).toHaveBeenCalledWith(customers);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Customers>>();
        const customers = { id: 123 };
        jest.spyOn(customersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ customers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(customersService.update).toHaveBeenCalledWith(customers);
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

      describe('trackCitiesById', () => {
        it('Should return tracked Cities primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCitiesById(0, entity);
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
    });
  });
});
