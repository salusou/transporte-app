jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmployeeComponentsDataService } from '../service/employee-components-data.service';
import { IEmployeeComponentsData, EmployeeComponentsData } from '../employee-components-data.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';

import { EmployeeComponentsDataUpdateComponent } from './employee-components-data-update.component';

describe('Component Tests', () => {
  describe('EmployeeComponentsData Management Update Component', () => {
    let comp: EmployeeComponentsDataUpdateComponent;
    let fixture: ComponentFixture<EmployeeComponentsDataUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let employeeComponentsDataService: EmployeeComponentsDataService;
    let employeesService: EmployeesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeeComponentsDataUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EmployeeComponentsDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeComponentsDataUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      employeeComponentsDataService = TestBed.inject(EmployeeComponentsDataService);
      employeesService = TestBed.inject(EmployeesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Employees query and add missing value', () => {
        const employeeComponentsData: IEmployeeComponentsData = { id: 456 };
        const employee: IEmployees = { id: 68982 };
        employeeComponentsData.employee = employee;

        const employeesCollection: IEmployees[] = [{ id: 29116 }];
        jest.spyOn(employeesService, 'query').mockReturnValue(of(new HttpResponse({ body: employeesCollection })));
        const additionalEmployees = [employee];
        const expectedCollection: IEmployees[] = [...additionalEmployees, ...employeesCollection];
        jest.spyOn(employeesService, 'addEmployeesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employeeComponentsData });
        comp.ngOnInit();

        expect(employeesService.query).toHaveBeenCalled();
        expect(employeesService.addEmployeesToCollectionIfMissing).toHaveBeenCalledWith(employeesCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const employeeComponentsData: IEmployeeComponentsData = { id: 456 };
        const employee: IEmployees = { id: 66688 };
        employeeComponentsData.employee = employee;

        activatedRoute.data = of({ employeeComponentsData });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(employeeComponentsData));
        expect(comp.employeesSharedCollection).toContain(employee);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EmployeeComponentsData>>();
        const employeeComponentsData = { id: 123 };
        jest.spyOn(employeeComponentsDataService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employeeComponentsData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employeeComponentsData }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(employeeComponentsDataService.update).toHaveBeenCalledWith(employeeComponentsData);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EmployeeComponentsData>>();
        const employeeComponentsData = new EmployeeComponentsData();
        jest.spyOn(employeeComponentsDataService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employeeComponentsData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employeeComponentsData }));
        saveSubject.complete();

        // THEN
        expect(employeeComponentsDataService.create).toHaveBeenCalledWith(employeeComponentsData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EmployeeComponentsData>>();
        const employeeComponentsData = { id: 123 };
        jest.spyOn(employeeComponentsDataService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employeeComponentsData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(employeeComponentsDataService.update).toHaveBeenCalledWith(employeeComponentsData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
