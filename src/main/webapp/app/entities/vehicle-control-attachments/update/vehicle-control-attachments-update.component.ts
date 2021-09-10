import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVehicleControlAttachments, VehicleControlAttachments } from '../vehicle-control-attachments.model';
import { VehicleControlAttachmentsService } from '../service/vehicle-control-attachments.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';

@Component({
  selector: 'jhi-vehicle-control-attachments-update',
  templateUrl: './vehicle-control-attachments-update.component.html',
})
export class VehicleControlAttachmentsUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlsSharedCollection: IVehicleControls[] = [];

  editForm = this.fb.group({
    id: [],
    attachImage: [],
    attachImageContentType: [],
    attachUrl: [null, [Validators.required]],
    attachDescription: [null, [Validators.required]],
    attachedDate: [null, [Validators.required]],
    vehicleControls: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected vehicleControlAttachmentsService: VehicleControlAttachmentsService,
    protected vehicleControlsService: VehicleControlsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlAttachments }) => {
      if (vehicleControlAttachments.id === undefined) {
        const today = dayjs().startOf('day');
        vehicleControlAttachments.attachedDate = today;
      }

      this.updateForm(vehicleControlAttachments);

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
    const vehicleControlAttachments = this.createFromForm();
    if (vehicleControlAttachments.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleControlAttachmentsService.update(vehicleControlAttachments));
    } else {
      this.subscribeToSaveResponse(this.vehicleControlAttachmentsService.create(vehicleControlAttachments));
    }
  }

  trackVehicleControlsById(index: number, item: IVehicleControls): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleControlAttachments>>): void {
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

  protected updateForm(vehicleControlAttachments: IVehicleControlAttachments): void {
    this.editForm.patchValue({
      id: vehicleControlAttachments.id,
      attachImage: vehicleControlAttachments.attachImage,
      attachImageContentType: vehicleControlAttachments.attachImageContentType,
      attachUrl: vehicleControlAttachments.attachUrl,
      attachDescription: vehicleControlAttachments.attachDescription,
      attachedDate: vehicleControlAttachments.attachedDate ? vehicleControlAttachments.attachedDate.format(DATE_TIME_FORMAT) : null,
      vehicleControls: vehicleControlAttachments.vehicleControls,
    });

    this.vehicleControlsSharedCollection = this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(
      this.vehicleControlsSharedCollection,
      vehicleControlAttachments.vehicleControls
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
  }

  protected createFromForm(): IVehicleControlAttachments {
    return {
      ...new VehicleControlAttachments(),
      id: this.editForm.get(['id'])!.value,
      attachImageContentType: this.editForm.get(['attachImageContentType'])!.value,
      attachImage: this.editForm.get(['attachImage'])!.value,
      attachUrl: this.editForm.get(['attachUrl'])!.value,
      attachDescription: this.editForm.get(['attachDescription'])!.value,
      attachedDate: this.editForm.get(['attachedDate'])!.value
        ? dayjs(this.editForm.get(['attachedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
    };
  }
}
