jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleControlBillingService } from '../service/vehicle-control-billing.service';
import { IVehicleControlBilling, VehicleControlBilling } from '../vehicle-control-billing.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { IFees } from 'app/entities/fees/fees.model';
import { FeesService } from 'app/entities/fees/service/fees.service';

import { VehicleControlBillingUpdateComponent } from './vehicle-control-billing-update.component';

describe('Component Tests', () => {
  describe('VehicleControlBilling Management Update Component', () => {
    let comp: VehicleControlBillingUpdateComponent;
    let fixture: ComponentFixture<VehicleControlBillingUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleControlBillingService: VehicleControlBillingService;
    let vehicleControlsService: VehicleControlsService;
    let feesService: FeesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleControlBillingUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleControlBillingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleControlBillingUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleControlBillingService = TestBed.inject(VehicleControlBillingService);
      vehicleControlsService = TestBed.inject(VehicleControlsService);
      feesService = TestBed.inject(FeesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControls query and add missing value', () => {
        const vehicleControlBilling: IVehicleControlBilling = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 90683 };
        vehicleControlBilling.vehicleControls = vehicleControls;

        const vehicleControlsCollection: IVehicleControls[] = [{ id: 55393 }];
        jest.spyOn(vehicleControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlsCollection })));
        const additionalVehicleControls = [vehicleControls];
        const expectedCollection: IVehicleControls[] = [...additionalVehicleControls, ...vehicleControlsCollection];
        jest.spyOn(vehicleControlsService, 'addVehicleControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlBilling });
        comp.ngOnInit();

        expect(vehicleControlsService.query).toHaveBeenCalled();
        expect(vehicleControlsService.addVehicleControlsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlsCollection,
          ...additionalVehicleControls
        );
        expect(comp.vehicleControlsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Fees query and add missing value', () => {
        const vehicleControlBilling: IVehicleControlBilling = { id: 456 };
        const fees: IFees = { id: 96921 };
        vehicleControlBilling.fees = fees;

        const feesCollection: IFees[] = [{ id: 16381 }];
        jest.spyOn(feesService, 'query').mockReturnValue(of(new HttpResponse({ body: feesCollection })));
        const additionalFees = [fees];
        const expectedCollection: IFees[] = [...additionalFees, ...feesCollection];
        jest.spyOn(feesService, 'addFeesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlBilling });
        comp.ngOnInit();

        expect(feesService.query).toHaveBeenCalled();
        expect(feesService.addFeesToCollectionIfMissing).toHaveBeenCalledWith(feesCollection, ...additionalFees);
        expect(comp.feesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleControlBilling: IVehicleControlBilling = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 61708 };
        vehicleControlBilling.vehicleControls = vehicleControls;
        const fees: IFees = { id: 16415 };
        vehicleControlBilling.fees = fees;

        activatedRoute.data = of({ vehicleControlBilling });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleControlBilling));
        expect(comp.vehicleControlsSharedCollection).toContain(vehicleControls);
        expect(comp.feesSharedCollection).toContain(fees);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlBilling>>();
        const vehicleControlBilling = { id: 123 };
        jest.spyOn(vehicleControlBillingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlBilling });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlBilling }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleControlBillingService.update).toHaveBeenCalledWith(vehicleControlBilling);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlBilling>>();
        const vehicleControlBilling = new VehicleControlBilling();
        jest.spyOn(vehicleControlBillingService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlBilling });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlBilling }));
        saveSubject.complete();

        // THEN
        expect(vehicleControlBillingService.create).toHaveBeenCalledWith(vehicleControlBilling);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlBilling>>();
        const vehicleControlBilling = { id: 123 };
        jest.spyOn(vehicleControlBillingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlBilling });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleControlBillingService.update).toHaveBeenCalledWith(vehicleControlBilling);
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

      describe('trackFeesById', () => {
        it('Should return tracked Fees primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFeesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
