jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ParkingSectorSpaceService } from '../service/parking-sector-space.service';
import { IParkingSectorSpace, ParkingSectorSpace } from '../parking-sector-space.model';
import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { ParkingSectorService } from 'app/entities/parking-sector/service/parking-sector.service';

import { ParkingSectorSpaceUpdateComponent } from './parking-sector-space-update.component';

describe('Component Tests', () => {
  describe('ParkingSectorSpace Management Update Component', () => {
    let comp: ParkingSectorSpaceUpdateComponent;
    let fixture: ComponentFixture<ParkingSectorSpaceUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let parkingSectorSpaceService: ParkingSectorSpaceService;
    let parkingSectorService: ParkingSectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ParkingSectorSpaceUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ParkingSectorSpaceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParkingSectorSpaceUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      parkingSectorSpaceService = TestBed.inject(ParkingSectorSpaceService);
      parkingSectorService = TestBed.inject(ParkingSectorService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call ParkingSector query and add missing value', () => {
        const parkingSectorSpace: IParkingSectorSpace = { id: 456 };
        const parkingSector: IParkingSector = { id: 17719 };
        parkingSectorSpace.parkingSector = parkingSector;

        const parkingSectorCollection: IParkingSector[] = [{ id: 59272 }];
        jest.spyOn(parkingSectorService, 'query').mockReturnValue(of(new HttpResponse({ body: parkingSectorCollection })));
        const additionalParkingSectors = [parkingSector];
        const expectedCollection: IParkingSector[] = [...additionalParkingSectors, ...parkingSectorCollection];
        jest.spyOn(parkingSectorService, 'addParkingSectorToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ parkingSectorSpace });
        comp.ngOnInit();

        expect(parkingSectorService.query).toHaveBeenCalled();
        expect(parkingSectorService.addParkingSectorToCollectionIfMissing).toHaveBeenCalledWith(
          parkingSectorCollection,
          ...additionalParkingSectors
        );
        expect(comp.parkingSectorsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const parkingSectorSpace: IParkingSectorSpace = { id: 456 };
        const parkingSector: IParkingSector = { id: 80324 };
        parkingSectorSpace.parkingSector = parkingSector;

        activatedRoute.data = of({ parkingSectorSpace });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(parkingSectorSpace));
        expect(comp.parkingSectorsSharedCollection).toContain(parkingSector);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ParkingSectorSpace>>();
        const parkingSectorSpace = { id: 123 };
        jest.spyOn(parkingSectorSpaceService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parkingSectorSpace });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parkingSectorSpace }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(parkingSectorSpaceService.update).toHaveBeenCalledWith(parkingSectorSpace);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ParkingSectorSpace>>();
        const parkingSectorSpace = new ParkingSectorSpace();
        jest.spyOn(parkingSectorSpaceService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parkingSectorSpace });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parkingSectorSpace }));
        saveSubject.complete();

        // THEN
        expect(parkingSectorSpaceService.create).toHaveBeenCalledWith(parkingSectorSpace);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ParkingSectorSpace>>();
        const parkingSectorSpace = { id: 123 };
        jest.spyOn(parkingSectorSpaceService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parkingSectorSpace });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(parkingSectorSpaceService.update).toHaveBeenCalledWith(parkingSectorSpace);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackParkingSectorById', () => {
        it('Should return tracked ParkingSector primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackParkingSectorById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
