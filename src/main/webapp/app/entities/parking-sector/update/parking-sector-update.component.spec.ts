jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ParkingSectorService } from '../service/parking-sector.service';
import { IParkingSector, ParkingSector } from '../parking-sector.model';
import { IParking } from 'app/entities/parking/parking.model';
import { ParkingService } from 'app/entities/parking/service/parking.service';

import { ParkingSectorUpdateComponent } from './parking-sector-update.component';

describe('Component Tests', () => {
  describe('ParkingSector Management Update Component', () => {
    let comp: ParkingSectorUpdateComponent;
    let fixture: ComponentFixture<ParkingSectorUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let parkingSectorService: ParkingSectorService;
    let parkingService: ParkingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ParkingSectorUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ParkingSectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParkingSectorUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      parkingSectorService = TestBed.inject(ParkingSectorService);
      parkingService = TestBed.inject(ParkingService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Parking query and add missing value', () => {
        const parkingSector: IParkingSector = { id: 456 };
        const parking: IParking = { id: 16597 };
        parkingSector.parking = parking;

        const parkingCollection: IParking[] = [{ id: 9793 }];
        jest.spyOn(parkingService, 'query').mockReturnValue(of(new HttpResponse({ body: parkingCollection })));
        const additionalParkings = [parking];
        const expectedCollection: IParking[] = [...additionalParkings, ...parkingCollection];
        jest.spyOn(parkingService, 'addParkingToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ parkingSector });
        comp.ngOnInit();

        expect(parkingService.query).toHaveBeenCalled();
        expect(parkingService.addParkingToCollectionIfMissing).toHaveBeenCalledWith(parkingCollection, ...additionalParkings);
        expect(comp.parkingsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const parkingSector: IParkingSector = { id: 456 };
        const parking: IParking = { id: 23088 };
        parkingSector.parking = parking;

        activatedRoute.data = of({ parkingSector });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(parkingSector));
        expect(comp.parkingsSharedCollection).toContain(parking);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ParkingSector>>();
        const parkingSector = { id: 123 };
        jest.spyOn(parkingSectorService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parkingSector });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parkingSector }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(parkingSectorService.update).toHaveBeenCalledWith(parkingSector);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ParkingSector>>();
        const parkingSector = new ParkingSector();
        jest.spyOn(parkingSectorService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parkingSector });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parkingSector }));
        saveSubject.complete();

        // THEN
        expect(parkingSectorService.create).toHaveBeenCalledWith(parkingSector);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ParkingSector>>();
        const parkingSector = { id: 123 };
        jest.spyOn(parkingSectorService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parkingSector });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(parkingSectorService.update).toHaveBeenCalledWith(parkingSector);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackParkingById', () => {
        it('Should return tracked Parking primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackParkingById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
