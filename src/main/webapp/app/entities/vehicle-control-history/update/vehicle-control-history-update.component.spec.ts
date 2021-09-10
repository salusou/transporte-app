jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VehicleControlHistoryService } from '../service/vehicle-control-history.service';
import { IVehicleControlHistory, VehicleControlHistory } from '../vehicle-control-history.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';

import { VehicleControlHistoryUpdateComponent } from './vehicle-control-history-update.component';

describe('Component Tests', () => {
  describe('VehicleControlHistory Management Update Component', () => {
    let comp: VehicleControlHistoryUpdateComponent;
    let fixture: ComponentFixture<VehicleControlHistoryUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vehicleControlHistoryService: VehicleControlHistoryService;
    let vehicleControlsService: VehicleControlsService;
    let employeesService: EmployeesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VehicleControlHistoryUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VehicleControlHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleControlHistoryUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vehicleControlHistoryService = TestBed.inject(VehicleControlHistoryService);
      vehicleControlsService = TestBed.inject(VehicleControlsService);
      employeesService = TestBed.inject(EmployeesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call VehicleControls query and add missing value', () => {
        const vehicleControlHistory: IVehicleControlHistory = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 44488 };
        vehicleControlHistory.vehicleControls = vehicleControls;

        const vehicleControlsCollection: IVehicleControls[] = [{ id: 7161 }];
        jest.spyOn(vehicleControlsService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleControlsCollection })));
        const additionalVehicleControls = [vehicleControls];
        const expectedCollection: IVehicleControls[] = [...additionalVehicleControls, ...vehicleControlsCollection];
        jest.spyOn(vehicleControlsService, 'addVehicleControlsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlHistory });
        comp.ngOnInit();

        expect(vehicleControlsService.query).toHaveBeenCalled();
        expect(vehicleControlsService.addVehicleControlsToCollectionIfMissing).toHaveBeenCalledWith(
          vehicleControlsCollection,
          ...additionalVehicleControls
        );
        expect(comp.vehicleControlsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Employees query and add missing value', () => {
        const vehicleControlHistory: IVehicleControlHistory = { id: 456 };
        const employees: IEmployees = { id: 58409 };
        vehicleControlHistory.employees = employees;

        const employeesCollection: IEmployees[] = [{ id: 218 }];
        jest.spyOn(employeesService, 'query').mockReturnValue(of(new HttpResponse({ body: employeesCollection })));
        const additionalEmployees = [employees];
        const expectedCollection: IEmployees[] = [...additionalEmployees, ...employeesCollection];
        jest.spyOn(employeesService, 'addEmployeesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vehicleControlHistory });
        comp.ngOnInit();

        expect(employeesService.query).toHaveBeenCalled();
        expect(employeesService.addEmployeesToCollectionIfMissing).toHaveBeenCalledWith(employeesCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vehicleControlHistory: IVehicleControlHistory = { id: 456 };
        const vehicleControls: IVehicleControls = { id: 4793 };
        vehicleControlHistory.vehicleControls = vehicleControls;
        const employees: IEmployees = { id: 65940 };
        vehicleControlHistory.employees = employees;

        activatedRoute.data = of({ vehicleControlHistory });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleControlHistory));
        expect(comp.vehicleControlsSharedCollection).toContain(vehicleControls);
        expect(comp.employeesSharedCollection).toContain(employees);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlHistory>>();
        const vehicleControlHistory = { id: 123 };
        jest.spyOn(vehicleControlHistoryService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlHistory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlHistory }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vehicleControlHistoryService.update).toHaveBeenCalledWith(vehicleControlHistory);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlHistory>>();
        const vehicleControlHistory = new VehicleControlHistory();
        jest.spyOn(vehicleControlHistoryService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlHistory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vehicleControlHistory }));
        saveSubject.complete();

        // THEN
        expect(vehicleControlHistoryService.create).toHaveBeenCalledWith(vehicleControlHistory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VehicleControlHistory>>();
        const vehicleControlHistory = { id: 123 };
        jest.spyOn(vehicleControlHistoryService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vehicleControlHistory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vehicleControlHistoryService.update).toHaveBeenCalledWith(vehicleControlHistory);
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

      describe('trackEmployeesById', () => {
        it('Should return tracked Employees primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmployeesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
