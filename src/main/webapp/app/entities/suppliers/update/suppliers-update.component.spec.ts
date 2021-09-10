jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SuppliersService } from '../service/suppliers.service';
import { ISuppliers, Suppliers } from '../suppliers.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IServiceProvided } from 'app/entities/service-provided/service-provided.model';
import { ServiceProvidedService } from 'app/entities/service-provided/service/service-provided.service';

import { SuppliersUpdateComponent } from './suppliers-update.component';

describe('Component Tests', () => {
  describe('Suppliers Management Update Component', () => {
    let comp: SuppliersUpdateComponent;
    let fixture: ComponentFixture<SuppliersUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let suppliersService: SuppliersService;
    let affiliatesService: AffiliatesService;
    let citiesService: CitiesService;
    let serviceProvidedService: ServiceProvidedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SuppliersUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SuppliersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuppliersUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      suppliersService = TestBed.inject(SuppliersService);
      affiliatesService = TestBed.inject(AffiliatesService);
      citiesService = TestBed.inject(CitiesService);
      serviceProvidedService = TestBed.inject(ServiceProvidedService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const suppliers: ISuppliers = { id: 456 };
        const affiliates: IAffiliates = { id: 33522 };
        suppliers.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 58271 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const suppliers: ISuppliers = { id: 456 };
        const cities: ICities = { id: 76158 };
        suppliers.cities = cities;

        const citiesCollection: ICities[] = [{ id: 61193 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ServiceProvided query and add missing value', () => {
        const suppliers: ISuppliers = { id: 456 };
        const serviceProvided: IServiceProvided = { id: 55029 };
        suppliers.serviceProvided = serviceProvided;

        const serviceProvidedCollection: IServiceProvided[] = [{ id: 61909 }];
        jest.spyOn(serviceProvidedService, 'query').mockReturnValue(of(new HttpResponse({ body: serviceProvidedCollection })));
        const additionalServiceProvideds = [serviceProvided];
        const expectedCollection: IServiceProvided[] = [...additionalServiceProvideds, ...serviceProvidedCollection];
        jest.spyOn(serviceProvidedService, 'addServiceProvidedToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        expect(serviceProvidedService.query).toHaveBeenCalled();
        expect(serviceProvidedService.addServiceProvidedToCollectionIfMissing).toHaveBeenCalledWith(
          serviceProvidedCollection,
          ...additionalServiceProvideds
        );
        expect(comp.serviceProvidedsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const suppliers: ISuppliers = { id: 456 };
        const affiliates: IAffiliates = { id: 23404 };
        suppliers.affiliates = affiliates;
        const cities: ICities = { id: 56850 };
        suppliers.cities = cities;
        const serviceProvided: IServiceProvided = { id: 95328 };
        suppliers.serviceProvided = serviceProvided;

        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(suppliers));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.citiesSharedCollection).toContain(cities);
        expect(comp.serviceProvidedsSharedCollection).toContain(serviceProvided);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Suppliers>>();
        const suppliers = { id: 123 };
        jest.spyOn(suppliersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: suppliers }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(suppliersService.update).toHaveBeenCalledWith(suppliers);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Suppliers>>();
        const suppliers = new Suppliers();
        jest.spyOn(suppliersService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: suppliers }));
        saveSubject.complete();

        // THEN
        expect(suppliersService.create).toHaveBeenCalledWith(suppliers);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Suppliers>>();
        const suppliers = { id: 123 };
        jest.spyOn(suppliersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ suppliers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(suppliersService.update).toHaveBeenCalledWith(suppliers);
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

      describe('trackServiceProvidedById', () => {
        it('Should return tracked ServiceProvided primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackServiceProvidedById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
