jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleControlAttachmentsService } from '../service/vehicle-control-attachments.service';
import { IVehicleControlAttachments, VehicleControlAttachments } from '../vehicle-control-attachments.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';

import { VehicleControlAttachmentsUpdateComponent } from './vehicle-control-attachments-update.component';

describe('Component Tests', () => {
  describe('VehicleControlAttachments Management Update Component', () => {
    let comp: VehicleControlAttachmentsUpdateComponent;
    let fixture: ComponentFixture<VehicleControlAttachmentsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleControlAttachmentsService: VehicleControlAttachmentsService;
    let vehicleControlsService: VehicleControlsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleControlAttachmentsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleControlAttachmentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleControlAttachmentsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleControlAttachmentsService = TestBed.inject(VehicleControlAttachmentsService);
      vehicleControlsService = TestBed.inject(VehicleControlsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControls query and add missing value', () => {
        const vehicleControlAttachments: IVehicleControlAttachments = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 11681 };
        vehicleControlAttachments.vehicleControls = vehicleControls;

        const vehicleControlsCollection: IVehicleControls[] = [{ id: 64736 }];
        jest.spyOn(vehicleControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlsCollection })));
        const additionalVehicleControls = [vehicleControls];
        const expectedCollection: IVehicleControls[] = [...additionalVehicleControls, ...vehicleControlsCollection];
        jest.spyOn(vehicleControlsService, 'addVehicleControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlAttachments });
        comp.ngOnInit();

        expect(vehicleControlsService.query).toHaveBeenCalled();
        expect(vehicleControlsService.addVehicleControlsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlsCollection,
          ...additionalVehicleControls
        );
        expect(comp.vehicleControlsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleControlAttachments: IVehicleControlAttachments = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 93745 };
        vehicleControlAttachments.vehicleControls = vehicleControls;

        activatedRoute.data = of({ vehicleControlAttachments });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleControlAttachments));
        expect(comp.vehicleControlsSharedCollection).toContain(vehicleControls);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlAttachments>>();
        const vehicleControlAttachments = { id: 123 };
        jest.spyOn(vehicleControlAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlAttachments }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleControlAttachmentsService.update).toHaveBeenCalledWith(vehicleControlAttachments);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlAttachments>>();
        const vehicleControlAttachments = new VehicleControlAttachments();
        jest.spyOn(vehicleControlAttachmentsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlAttachments }));
        saveSubject.complete();

        // THEN
        expect(vehicleControlAttachmentsService.create).toHaveBeenCalledWith(vehicleControlAttachments);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlAttachments>>();
        const vehicleControlAttachments = { id: 123 };
        jest.spyOn(vehicleControlAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleControlAttachmentsService.update).toHaveBeenCalledWith(vehicleControlAttachments);
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
