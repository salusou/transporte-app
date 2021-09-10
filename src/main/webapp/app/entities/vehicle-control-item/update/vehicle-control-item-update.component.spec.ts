jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleControlItemService } from '../service/vehicle-control-item.service';
import { IVehicleControlItem, VehicleControlItem } from '../vehicle-control-item.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';

import { VehicleControlItemUpdateComponent } from './vehicle-control-item-update.component';

describe('Component Tests', () => {
  describe('VehicleControlItem Management Update Component', () => {
    let comp: VehicleControlItemUpdateComponent;
    let fixture: ComponentFixture<VehicleControlItemUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleControlItemService: VehicleControlItemService;
    let vehicleControlsService: VehicleControlsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleControlItemUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleControlItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleControlItemUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleControlItemService = TestBed.inject(VehicleControlItemService);
      vehicleControlsService = TestBed.inject(VehicleControlsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControls query and add missing value', () => {
        const vehicleControlItem: IVehicleControlItem = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 53216 };
        vehicleControlItem.vehicleControls = vehicleControls;

        const vehicleControlsCollection: IVehicleControls[] = [{ id: 79799 }];
        jest.spyOn(vehicleControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlsCollection })));
        const additionalVehicleControls = [vehicleControls];
        const expectedCollection: IVehicleControls[] = [...additionalVehicleControls, ...vehicleControlsCollection];
        jest.spyOn(vehicleControlsService, 'addVehicleControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlItem });
        comp.ngOnInit();

        expect(vehicleControlsService.query).toHaveBeenCalled();
        expect(vehicleControlsService.addVehicleControlsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlsCollection,
          ...additionalVehicleControls
        );
        expect(comp.vehicleControlsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleControlItem: IVehicleControlItem = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 92986 };
        vehicleControlItem.vehicleControls = vehicleControls;

        activatedRoute.data = of({ vehicleControlItem });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleControlItem));
        expect(comp.vehicleControlsSharedCollection).toContain(vehicleControls);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlItem>>();
        const vehicleControlItem = { id: 123 };
        jest.spyOn(vehicleControlItemService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlItem }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleControlItemService.update).toHaveBeenCalledWith(vehicleControlItem);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlItem>>();
        const vehicleControlItem = new VehicleControlItem();
        jest.spyOn(vehicleControlItemService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlItem }));
        saveSubject.complete();

        // THEN
        expect(vehicleControlItemService.create).toHaveBeenCalledWith(vehicleControlItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlItem>>();
        const vehicleControlItem = { id: 123 };
        jest.spyOn(vehicleControlItemService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlItem });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleControlItemService.update).toHaveBeenCalledWith(vehicleControlItem);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVehicleControlsById', () => {
        it('Should return tracked VehicleControls primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVehicleControlsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
