jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ParkingService } from '../service/parking.service';
import { IParking, Parking } from '../parking.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

import { ParkingUpdateComponent } from './parking-update.component';

describe('Component Tests', () => {
  describe('Parking Management Update Component', () => {
    let comp: ParkingUpdateComponent;
    let fixture: ComponentFixture<ParkingUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let parkingService: ParkingService;
    let affiliatesService: AffiliatesService;
    let citiesService: CitiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ParkingUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ParkingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParkingUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      parkingService = TestBed.inject(ParkingService);
      affiliatesService = TestBed.inject(AffiliatesService);
      citiesService = TestBed.inject(CitiesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Affiliates query and add missing value', () => {
        const parking: IParking = { id: 456 };
        const affiliates: IAffiliates = { id: 21425 };
        parking.affiliates = affiliates;

        const affiliatesCollection: IAffiliates[] = [{ id: 95853 }];
        jest.spyOn(affiliatesService, 'query').mockReturnValue(of(new HttpResponse({ body: affiliatesCollection })));
        const additionalAffiliates = [affiliates];
        const expectedCollection: IAffiliates[] = [...additionalAffiliates, ...affiliatesCollection];
        jest.spyOn(affiliatesService, 'addAffiliatesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ parking });
        comp.ngOnInit();

        expect(affiliatesService.query).toHaveBeenCalled();
        expect(affiliatesService.addAffiliatesToCollectionIfMissing).toHaveBeenCalledWith(affiliatesCollection, ...additionalAffiliates);
        expect(comp.affiliatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Cities query and add missing value', () => {
        const parking: IParking = { id: 456 };
        const cities: ICities = { id: 19051 };
        parking.cities = cities;

        const citiesCollection: ICities[] = [{ id: 99448 }];
        jest.spyOn(citiesService, 'query').mockReturnValue(of(new HttpResponse({ body: citiesCollection })));
        const additionalCities = [cities];
        const expectedCollection: ICities[] = [...additionalCities, ...citiesCollection];
        jest.spyOn(citiesService, 'addCitiesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ parking });
        comp.ngOnInit();

        expect(citiesService.query).toHaveBeenCalled();
        expect(citiesService.addCitiesToCollectionIfMissing).toHaveBeenCalledWith(citiesCollection, ...additionalCities);
        expect(comp.citiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const parking: IParking = { id: 456 };
        const affiliates: IAffiliates = { id: 35929 };
        parking.affiliates = affiliates;
        const cities: ICities = { id: 34489 };
        parking.cities = cities;

        activatedRoute.data = of({ parking });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(parking));
        expect(comp.affiliatesSharedCollection).toContain(affiliates);
        expect(comp.citiesSharedCollection).toContain(cities);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Parking>>();
        const parking = { id: 123 };
        jest.spyOn(parkingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parking });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parking }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(parkingService.update).toHaveBeenCalledWith(parking);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Parking>>();
        const parking = new Parking();
        jest.spyOn(parkingService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parking });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parking }));
        saveSubject.complete();

        // THEN
        expect(parkingService.create).toHaveBeenCalledWith(parking);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Parking>>();
        const parking = { id: 123 };
        jest.spyOn(parkingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parking });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(parkingService.update).toHaveBeenCalledWith(parking);
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
    });
  });
});
