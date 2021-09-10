import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmployeeComponentsData, EmployeeComponentsData } from '../employee-components-data.model';
import { EmployeeComponentsDataService } from '../service/employee-components-data.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';

@Component({
  selector: 'jhi-employee-components-data-update',
  templateUrl: './employee-components-data-update.component.html',
})
export class EmployeeComponentsDataUpdateComponent implements OnInit {
  isSaving = false;

  employeesSharedCollection: IEmployees[] = [];

  editForm = this.fb.group({
    id: [],
    dataInfo: [],
    employee: [null, Validators.required],
  });

  constructor(
    protected employeeComponentsDataService: EmployeeComponentsDataService,
    protected employeesService: EmployeesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeComponentsData }) => {
      this.updateForm(employeeComponentsData);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeComponentsData = this.createFromForm();
    if (employeeComponentsData.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeComponentsDataService.update(employeeComponentsData));
    } else {
      this.subscribeToSaveResponse(this.employeeComponentsDataService.create(employeeComponentsData));
    }
  }

  trackEmployeesById(index: number, item: IEmployees): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeComponentsData>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(employeeComponentsData: IEmployeeComponentsData): void {
    this.editForm.patchValue({
      id: employeeComponentsData.id,
      dataInfo: employeeComponentsData.dataInfo,
      employee: employeeComponentsData.employee,
    });

    this.employeesSharedCollection = this.employeesService.addEmployeesToCollectionIfMissing(
      this.employeesSharedCollection,
      employeeComponentsData.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeesService
      .query()
      .pipe(map((res: HttpResponse<IEmployees[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployees[]) =>
          this.employeesService.addEmployeesToCollectionIfMissing(employees, this.editForm.get('employee')!.value)
        )
      )
      .subscribe((employees: IEmployees[]) => (this.employeesSharedCollection = employees));
  }

  protected createFromForm(): IEmployeeComponentsData {
    return {
      ...new EmployeeComponentsData(),
      id: this.editForm.get(['id'])!.value,
      dataInfo: this.editForm.get(['dataInfo'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }
}
