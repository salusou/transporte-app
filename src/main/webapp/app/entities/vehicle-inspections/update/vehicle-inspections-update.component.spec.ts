jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleInspectionsService } from '../service/vehicle-inspections.service';
import { IVehicleInspections, VehicleInspections } from '../vehicle-inspections.model';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { VehicleControlItemService } from 'app/entities/vehicle-control-item/service/vehicle-control-item.service';

import { VehicleInspectionsUpdateComponent } from './vehicle-inspections-update.component';

describe('Component Tests', () => {
  describe('VehicleInspections Management Update Component', () => {
    let comp: VehicleInspectionsUpdateComponent;
    let fixture: ComponentFixture<VehicleInspectionsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleInspectionsService: VehicleInspectionsService;
    let vehicleControlItemService: VehicleControlItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleInspectionsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleInspectionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleInspectionsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleInspectionsService = TestBed.inject(VehicleInspectionsService);
      vehicleControlItemService = TestBed.inject(VehicleControlItemService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControlItem query and add missing value', () => {
        const vehicleInspections: IVehicleInspections = { id: 456 };
        const vehicleControls: IVehicleControlItem = { id: 89041 };
        vehicleInspections.vehicleControls = vehicleControls;

        const vehicleControlItemCollection: IVehicleControlItem[] = [{ id: 33282 }];
        jest.spyOn(vehicleControlItemService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlItemCollection })));
        const additionalVehicleControlItems = [vehicleControls];
        const expectedCollection: IVehicleControlItem[] = [...additionalVehicleControlItems, ...vehicleControlItemCollection];
        jest.spyOn(vehicleControlItemService, 'addVehicleControlItemToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleInspections });
        comp.ngOnInit();

        expect(vehicleControlItemService.query).toHaveBeenCalled();
        expect(vehicleControlItemService.addVehicleControlItemToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlItemCollection,
          ...additionalVehicleControlItems
        );
        expect(comp.vehicleControlItemsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleInspections: IVehicleInspections = { id: 456 };
        const vehicleControls: IVehicleControlItem = { id: 87207 };
        vehicleInspections.vehicleControls = vehicleControls;

        activatedRoute.data = of({ vehicleInspections });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleInspections));
        expect(comp.vehicleControlItemsSharedCollection).toContain(vehicleControls);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleInspections>>();
        const vehicleInspections = { id: 123 };
        jest.spyOn(vehicleInspectionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleInspections });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleInspections }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleInspectionsService.update).toHaveBeenCalledWith(vehicleInspections);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleInspections>>();
        const vehicleInspections = new VehicleInspections();
        jest.spyOn(vehicleInspectionsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleInspections });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleInspections }));
        saveSubject.complete();

        // THEN
        expect(vehicleInspectionsService.create).toHaveBeenCalledWith(vehicleInspections);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleInspections>>();
        const vehicleInspections = { id: 123 };
        jest.spyOn(vehicleInspectionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleInspections });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleInspectionsService.update).toHaveBeenCalledWith(vehicleInspections);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
