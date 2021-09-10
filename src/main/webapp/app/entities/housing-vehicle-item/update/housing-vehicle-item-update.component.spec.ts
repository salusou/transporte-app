jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HousingVehicleItemService } from '../service/housing-vehicle-item.service';
import { IHousingVehicleItem, HousingVehicleItem } from '../housing-vehicle-item.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { HousingService } from 'app/entities/housing/service/housing.service';
import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { ParkingSectorService } from 'app/entities/parking-sector/service/parking-sector.service';
import { IParkingSectorSpace } from 'app/entities/parking-sector-space/parking-sector-space.model';
import { ParkingSectorSpaceService } from 'app/entities/parking-sector-space/service/parking-sector-space.service';

import { HousingVehicleItemUpdateComponent } from './housing-vehicle-item-update.component';

describe('Component Tests', () => {
  describe('HousingVehicleItem Management Update Component', () => {
    let comp: HousingVehicleItemUpdateComponent;
    let fixture: ComponentFixture<HousingVehicleItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let housingVehicleItemService: HousingVehicleItemService;
    let housingService: HousingService;
    let parkingSectorService: ParkingSectorService;
    let parkingSectorSpaceService: ParkingSectorSpaceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HousingVehicleItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HousingVehicleItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HousingVehicleItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      housingVehicleItemService = TestBed.inject(HousingVehicleItemService);
      housingService = TestBed.inject(HousingService);
      parkingSectorService = TestBed.inject(ParkingSectorService);
      parkingSectorSpaceService = TestBed.inject(ParkingSectorSpaceService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Housing query and add missing value', () => {
        const housingVehicleItem: IHousingVehicleItem = { id: 456 };
        const housing: IHousing = { id: 71367 };
        housingVehicleItem.housing = housing;

        const housingCollection: IHousing[] = [{ id: 7881 }];
        jest.spyOn(housingService, 'query').mockReturnValue(of(new HttpResponse({ body: housingCollection })));
        const additionalHousings = [housing];
        const expectedCollection: IHousing[] = [...additionalHousings, ...housingCollection];
        jest.spyOn(housingService, 'addHousingToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        expect(housingService.query).toHaveBeenCalled();
        expect(housingService.addHousingToCollectionIfMissing).toHaveBeenCalledWith(housingCollection, ...additionalHousings);
        expect(comp.housingsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ParkingSector query and add missing value', () => {
        const housingVehicleItem: IHousingVehicleItem = { id: 456 };
        const parkingSector: IParkingSector = { id: 93863 };
        housingVehicleItem.parkingSector = parkingSector;

        const parkingSectorCollection: IParkingSector[] = [{ id: 33256 }];
        jest.spyOn(parkingSectorService, 'query').mockReturnValue(of(new HttpResponse({ body: parkingSectorCollection })));
        const additionalParkingSectors = [parkingSector];
        const expectedCollection: IParkingSector[] = [...additionalParkingSectors, ...parkingSectorCollection];
        jest.spyOn(parkingSectorService, 'addParkingSectorToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        expect(parkingSectorService.query).toHaveBeenCalled();
        expect(parkingSectorService.addParkingSectorToCollectionIfMissing).toHaveBeenCalledWith(
          parkingSectorCollection,
          ...additionalParkingSectors
        );
        expect(comp.parkingSectorsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ParkingSectorSpace query and add missing value', () => {
        const housingVehicleItem: IHousingVehicleItem = { id: 456 };
        const parkingSectorSpace: IParkingSectorSpace = { id: 62447 };
        housingVehicleItem.parkingSectorSpace = parkingSectorSpace;

        const parkingSectorSpaceCollection: IParkingSectorSpace[] = [{ id: 24715 }];
        jest.spyOn(parkingSectorSpaceService, 'query').mockReturnValue(of(new HttpResponse({ body: parkingSectorSpaceCollection })));
        const additionalParkingSectorSpaces = [parkingSectorSpace];
        const expectedCollection: IParkingSectorSpace[] = [...additionalParkingSectorSpaces, ...parkingSectorSpaceCollection];
        jest.spyOn(parkingSectorSpaceService, 'addParkingSectorSpaceToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        expect(parkingSectorSpaceService.query).toHaveBeenCalled();
        expect(parkingSectorSpaceService.addParkingSectorSpaceToCollectionIfMissing).toHaveBeenCalledWith(
          parkingSectorSpaceCollection,
          ...additionalParkingSectorSpaces
        );
        expect(comp.parkingSectorSpacesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const housingVehicleItem: IHousingVehicleItem = { id: 456 };
        const housing: IHousing = { id: 14077 };
        housingVehicleItem.housing = housing;
        const parkingSector: IParkingSector = { id: 42642 };
        housingVehicleItem.parkingSector = parkingSector;
        const parkingSectorSpace: IParkingSectorSpace = { id: 98394 };
        housingVehicleItem.parkingSectorSpace = parkingSectorSpace;

        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(housingVehicleItem));
        expect(comp.housingsSharedCollection).toContain(housing);
        expect(comp.parkingSectorsSharedCollection).toContain(parkingSector);
        expect(comp.parkingSectorSpacesSharedCollection).toContain(parkingSectorSpace);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HousingVehicleItem>>();
        const housingVehicleItem = { id: 123 };
        jest.spyOn(housingVehicleItemService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: housingVehicleItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(housingVehicleItemService.update).toHaveBeenCalledWith(housingVehicleItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HousingVehicleItem>>();
        const housingVehicleItem = new HousingVehicleItem();
        jest.spyOn(housingVehicleItemService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: housingVehicleItem }));
        saveSubject.complete();

        // THEN
        expect(housingVehicleItemService.create).toHaveBeenCalledWith(housingVehicleItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HousingVehicleItem>>();
        const housingVehicleItem = { id: 123 };
        jest.spyOn(housingVehicleItemService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ housingVehicleItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(housingVehicleItemService.update).toHaveBeenCalledWith(housingVehicleItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackHousingById', () => {
        it('Should return tracked Housing primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHousingById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackParkingSectorById', () => {
        it('Should return tracked ParkingSector primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackParkingSectorById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackParkingSectorSpaceById', () => {
        it('Should return tracked ParkingSectorSpace primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackParkingSectorSpaceById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
