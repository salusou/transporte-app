import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEmployeeAttachments, EmployeeAttachments } from '../employee-attachments.model';
import { EmployeeAttachmentsService } from '../service/employee-attachments.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';

@Component({
  selector: 'jhi-employee-attachments-update',
  templateUrl: './employee-attachments-update.component.html',
})
export class EmployeeAttachmentsUpdateComponent implements OnInit {
  isSaving = false;

  employeesSharedCollection: IEmployees[] = [];

  editForm = this.fb.group({
    id: [],
    attachImage: [],
    attachImageContentType: [],
    attachUrl: [null, [Validators.required]],
    attachDescription: [null, [Validators.required]],
    attachedDate: [null, [Validators.required]],
    employees: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected employeeAttachmentsService: EmployeeAttachmentsService,
    protected employeesService: EmployeesService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeAttachments }) => {
      if (employeeAttachments.id === undefined) {
        const today = dayjs().startOf('day');
        employeeAttachments.attachedDate = today;
      }

      this.updateForm(employeeAttachments);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('transporteApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeAttachments = this.createFromForm();
    if (employeeAttachments.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeAttachmentsService.update(employeeAttachments));
    } else {
      this.subscribeToSaveResponse(this.employeeAttachmentsService.create(employeeAttachments));
    }
  }

  trackEmployeesById(index: number, item: IEmployees): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeAttachments>>): void {
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

  protected updateForm(employeeAttachments: IEmployeeAttachments): void {
    this.editForm.patchValue({
      id: employeeAttachments.id,
      attachImage: employeeAttachments.attachImage,
      attachImageContentType: employeeAttachments.attachImageContentType,
      attachUrl: employeeAttachments.attachUrl,
      attachDescription: employeeAttachments.attachDescription,
      attachedDate: employeeAttachments.attachedDate ? employeeAttachments.attachedDate.format(DATE_TIME_FORMAT) : null,
      employees: employeeAttachments.employees,
    });

    this.employeesSharedCollection = this.employeesService.addEmployeesToCollectionIfMissing(
      this.employeesSharedCollection,
      employeeAttachments.employees
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IEmployeeAttachments {
    return {
      ...new EmployeeAttachments(),
      id: this.editForm.get(['id'])!.value,
      attachImageContentType: this.editForm.get(['attachImageContentType'])!.value,
      attachImage: this.editForm.get(['attachImage'])!.value,
      attachUrl: this.editForm.get(['attachUrl'])!.value,
      attachDescription: this.editForm.get(['attachDescription'])!.value,
      attachedDate: this.editForm.get(['attachedDate'])!.value
        ? dayjs(this.editForm.get(['attachedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      employees: this.editForm.get(['employees'])!.value,
    };
  }
}
