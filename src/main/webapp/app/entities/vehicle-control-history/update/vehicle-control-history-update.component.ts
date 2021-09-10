import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVehicleControlHistory, VehicleControlHistory } from '../vehicle-control-history.model';
import { VehicleControlHistoryService } from '../service/vehicle-control-history.service';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';

@Component({
  selector: 'jhi-vehicle-control-history-update',
  templateUrl: './vehicle-control-history-update.component.html',
})
export class VehicleControlHistoryUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlsSharedCollection: IVehicleControls[] = [];
  employeesSharedCollection: IEmployees[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleControlHistoryDate: [null, [Validators.required]],
    vehicleControlHistoryDescription: [null, [Validators.required, Validators.maxLength(500)]],
    vehicleControls: [null, Validators.required],
    employees: [null, Validators.required],
  });

  constructor(
    protected vehicleControlHistoryService: VehicleControlHistoryService,
    protected vehicleControlsService: VehicleControlsService,
    protected employeesService: EmployeesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlHistory }) => {
      if (vehicleControlHistory.id === undefined) {
        const today = dayjs().startOf('day');
        vehicleControlHistory.vehicleControlHistoryDate = today;
      }

      this.updateForm(vehicleControlHistory);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleControlHistory = this.createFromForm();
    if (vehicleControlHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleControlHistoryService.update(vehicleControlHistory));
    } else {
      this.subscribeToSaveResponse(this.vehicleControlHistoryService.create(vehicleControlHistory));
    }
  }

  trackVehicleControlsById(index: number, item: IVehicleControls): number {
    return item.id!;
  }

  trackEmployeesById(index: number, item: IEmployees): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleControlHistory>>): void {
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

  protected updateForm(vehicleControlHistory: IVehicleControlHistory): void {
    this.editForm.patchValue({
      id: vehicleControlHistory.id,
      vehicleControlHistoryDate: vehicleControlHistory.vehicleControlHistoryDate
        ? vehicleControlHistory.vehicleControlHistoryDate.format(DATE_TIME_FORMAT)
        : null,
      vehicleControlHistoryDescription: vehicleControlHistory.vehicleControlHistoryDescription,
      vehicleControls: vehicleControlHistory.vehicleControls,
      employees: vehicleControlHistory.employees,
    });

    this.vehicleControlsSharedCollection = this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(
      this.vehicleControlsSharedCollection,
      vehicleControlHistory.vehicleControls
    );
    this.employeesSharedCollection = this.employeesService.addEmployeesToCollectionIfMissing(
      this.employeesSharedCollection,
      vehicleControlHistory.employees
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vehicleControlsService
      .query()
      .pipe(map((res: HttpResponse<IVehicleControls[]>) => res.body ?? []))
      .pipe(
        map((vehicleControls: IVehicleControls[]) =>
          this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(vehicleControls, this.editForm.get('vehicleControls')!.value)
        )
      )
      .subscribe((vehicleControls: IVehicleControls[]) => (this.vehicleControlsSharedCollection = vehicleControls));

    this.employeesService
      .query()
      .pipe(map((res: HttpResponse<IEmployees[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployees[]) =>
          this.employeesService.addEmployeesToCollectionIfMissing(employees, this.editForm.get('employees')!.value)
        )
      )
      .subscribe((employees: IEmployees[]) => (this.employeesSharedCollection = employees));
  }

  protected createFromForm(): IVehicleControlHistory {
    return {
      ...new VehicleControlHistory(),
      id: this.editForm.get(['id'])!.value,
      vehicleControlHistoryDate: this.editForm.get(['vehicleControlHistoryDate'])!.value
        ? dayjs(this.editForm.get(['vehicleControlHistoryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      vehicleControlHistoryDescription: this.editForm.get(['vehicleControlHistoryDescription'])!.value,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
      employees: this.editForm.get(['employees'])!.value,
    };
  }
}
