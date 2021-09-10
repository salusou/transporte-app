import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICustomerAttachments, CustomerAttachments } from '../customer-attachments.model';
import { CustomerAttachmentsService } from '../service/customer-attachments.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';
import { IStatusAttachments } from 'app/entities/status-attachments/status-attachments.model';
import { StatusAttachmentsService } from 'app/entities/status-attachments/service/status-attachments.service';

@Component({
  selector: 'jhi-customer-attachments-update',
  templateUrl: './customer-attachments-update.component.html',
})
export class CustomerAttachmentsUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomers[] = [];
  statusAttachmentsSharedCollection: IStatusAttachments[] = [];

  editForm = this.fb.group({
    id: [],
    attachImage: [],
    attachImageContentType: [],
    attachUrl: [null, [Validators.required]],
    attachDescription: [null, [Validators.required]],
    attachedDate: [null, [Validators.required]],
    customers: [null, Validators.required],
    statusAttachments: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected customerAttachmentsService: CustomerAttachmentsService,
    protected customersService: CustomersService,
    protected statusAttachmentsService: StatusAttachmentsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerAttachments }) => {
      if (customerAttachments.id === undefined) {
        const today = dayjs().startOf('day');
        customerAttachments.attachedDate = today;
      }

      this.updateForm(customerAttachments);

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
    const customerAttachments = this.createFromForm();
    if (customerAttachments.id !== undefined) {
      this.subscribeToSaveResponse(this.customerAttachmentsService.update(customerAttachments));
    } else {
      this.subscribeToSaveResponse(this.customerAttachmentsService.create(customerAttachments));
    }
  }

  trackCustomersById(index: number, item: ICustomers): number {
    return item.id!;
  }

  trackStatusAttachmentsById(index: number, item: IStatusAttachments): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerAttachments>>): void {
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

  protected updateForm(customerAttachments: ICustomerAttachments): void {
    this.editForm.patchValue({
      id: customerAttachments.id,
      attachImage: customerAttachments.attachImage,
      attachImageContentType: customerAttachments.attachImageContentType,
      attachUrl: customerAttachments.attachUrl,
      attachDescription: customerAttachments.attachDescription,
      attachedDate: customerAttachments.attachedDate ? customerAttachments.attachedDate.format(DATE_TIME_FORMAT) : null,
      customers: customerAttachments.customers,
      statusAttachments: customerAttachments.statusAttachments,
    });

    this.customersSharedCollection = this.customersService.addCustomersToCollectionIfMissing(
      this.customersSharedCollection,
      customerAttachments.customers
    );
    this.statusAttachmentsSharedCollection = this.statusAttachmentsService.addStatusAttachmentsToCollectionIfMissing(
      this.statusAttachmentsSharedCollection,
      customerAttachments.statusAttachments
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customersService
      .query()
      .pipe(map((res: HttpResponse<ICustomers[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomers[]) =>
          this.customersService.addCustomersToCollectionIfMissing(customers, this.editForm.get('customers')!.value)
        )
      )
      .subscribe((customers: ICustomers[]) => (this.customersSharedCollection = customers));

    this.statusAttachmentsService
      .query()
      .pipe(map((res: HttpResponse<IStatusAttachments[]>) => res.body ?? []))
      .pipe(
        map((statusAttachments: IStatusAttachments[]) =>
          this.statusAttachmentsService.addStatusAttachmentsToCollectionIfMissing(
            statusAttachments,
            this.editForm.get('statusAttachments')!.value
          )
        )
      )
      .subscribe((statusAttachments: IStatusAttachments[]) => (this.statusAttachmentsSharedCollection = statusAttachments));
  }

  protected createFromForm(): ICustomerAttachments {
    return {
      ...new CustomerAttachments(),
      id: this.editForm.get(['id'])!.value,
      attachImageContentType: this.editForm.get(['attachImageContentType'])!.value,
      attachImage: this.editForm.get(['attachImage'])!.value,
      attachUrl: this.editForm.get(['attachUrl'])!.value,
      attachDescription: this.editForm.get(['attachDescription'])!.value,
      attachedDate: this.editForm.get(['attachedDate'])!.value
        ? dayjs(this.editForm.get(['attachedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      customers: this.editForm.get(['customers'])!.value,
      statusAttachments: this.editForm.get(['statusAttachments'])!.value,
    };
  }
}
