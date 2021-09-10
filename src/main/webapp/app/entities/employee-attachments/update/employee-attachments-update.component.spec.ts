jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmployeeAttachmentsService } from '../service/employee-attachments.service';
import { IEmployeeAttachments, EmployeeAttachments } from '../employee-attachments.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';

import { EmployeeAttachmentsUpdateComponent } from './employee-attachments-update.component';

describe('Component Tests', () => {
  describe('EmployeeAttachments Management Update Component', () => {
    let comp: EmployeeAttachmentsUpdateComponent;
    let fixture: ComponentFixture<EmployeeAttachmentsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let employeeAttachmentsService: EmployeeAttachmentsService;
    let employeesService: EmployeesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EmployeeAttachmentsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EmployeeAttachmentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeAttachmentsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      employeeAttachmentsService = TestBed.inject(EmployeeAttachmentsService);
      employeesService = TestBed.inject(EmployeesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Employees query and add missing value', () => {
        const employeeAttachments: IEmployeeAttachments = { id: 456 };
        const employees: IEmployees = { id: 57099 };
        employeeAttachments.employees = employees;

        const employeesCollection: IEmployees[] = [{ id: 81048 }];
        jest.spyOn(employeesService, 'query').mockReturnValue(of(new HttpResponse({ body: employeesCollection })));
        const additionalEmployees = [employees];
        const expectedCollection: IEmployees[] = [...additionalEmployees, ...employeesCollection];
        jest.spyOn(employeesService, 'addEmployeesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ employeeAttachments });
        comp.ngOnInit();

        expect(employeesService.query).toHaveBeenCalled();
        expect(employeesService.addEmployeesToCollectionIfMissing).toHaveBeenCalledWith(employeesCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const employeeAttachments: IEmployeeAttachments = { id: 456 };
        const employees: IEmployees = { id: 76223 };
        employeeAttachments.employees = employees;

        activatedRoute.data = of({ employeeAttachments });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(employeeAttachments));
        expect(comp.employeesSharedCollection).toContain(employees);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EmployeeAttachments>>();
        const employeeAttachments = { id: 123 };
        jest.spyOn(employeeAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employeeAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employeeAttachments }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(employeeAttachmentsService.update).toHaveBeenCalledWith(employeeAttachments);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EmployeeAttachments>>();
        const employeeAttachments = new EmployeeAttachments();
        jest.spyOn(employeeAttachmentsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employeeAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: employeeAttachments }));
        saveSubject.complete();

        // THEN
        expect(employeeAttachmentsService.create).toHaveBeenCalledWith(employeeAttachments);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EmployeeAttachments>>();
        const employeeAttachments = { id: 123 };
        jest.spyOn(employeeAttachmentsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ employeeAttachments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(employeeAttachmentsService.update).toHaveBeenCalledWith(employeeAttachments);
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
