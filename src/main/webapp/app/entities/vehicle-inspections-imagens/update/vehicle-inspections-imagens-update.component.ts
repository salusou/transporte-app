import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicleInspectionsImagens, VehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';
import { VehicleInspectionsImagensService } from '../service/vehicle-inspections-imagens.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IVehicleInspections } from 'app/entities/vehicle-inspections/vehicle-inspections.model';
import { VehicleInspectionsService } from 'app/entities/vehicle-inspections/service/vehicle-inspections.service';

@Component({
  selector: 'jhi-vehicle-inspections-imagens-update',
  templateUrl: './vehicle-inspections-imagens-update.component.html',
})
export class VehicleInspectionsImagensUpdateComponent implements OnInit {
  isSaving = false;

  vehicleInspectionsSharedCollection: IVehicleInspections[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleInspectionsImagensPositions: [null, [Validators.required]],
    vehicleInspectionsImagensStatus: [null, [Validators.required]],
    vehicleInspectionsImagensObs: [null, [Validators.maxLength(500)]],
    vehicleInspectionsImagensPhoto: [null, [Validators.required]],
    vehicleInspectionsImagensPhotoContentType: [],
    vehicleInspectionsImagensPositionsX: [null, [Validators.required]],
    vehicleInspectionsImagensPositionsY: [null, [Validators.required]],
    vehicleInspections: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected vehicleInspectionsImagensService: VehicleInspectionsImagensService,
    protected vehicleInspectionsService: VehicleInspectionsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleInspectionsImagens }) => {
      this.updateForm(vehicleInspectionsImagens);

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
    const vehicleInspectionsImagens = this.createFromForm();
    if (vehicleInspectionsImagens.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleInspectionsImagensService.update(vehicleInspectionsImagens));
    } else {
      this.subscribeToSaveResponse(this.vehicleInspectionsImagensService.create(vehicleInspectionsImagens));
    }
  }

  trackVehicleInspectionsById(index: number, item: IVehicleInspections): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleInspectionsImagens>>): void {
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

  protected updateForm(vehicleInspectionsImagens: IVehicleInspectionsImagens): void {
    this.editForm.patchValue({
      id: vehicleInspectionsImagens.id,
      vehicleInspectionsImagensPositions: vehicleInspectionsImagens.vehicleInspectionsImagensPositions,
      vehicleInspectionsImagensStatus: vehicleInspectionsImagens.vehicleInspectionsImagensStatus,
      vehicleInspectionsImagensObs: vehicleInspectionsImagens.vehicleInspectionsImagensObs,
      vehicleInspectionsImagensPhoto: vehicleInspectionsImagens.vehicleInspectionsImagensPhoto,
      vehicleInspectionsImagensPhotoContentType: vehicleInspectionsImagens.vehicleInspectionsImagensPhotoContentType,
      vehicleInspectionsImagensPositionsX: vehicleInspectionsImagens.vehicleInspectionsImagensPositionsX,
      vehicleInspectionsImagensPositionsY: vehicleInspectionsImagens.vehicleInspectionsImagensPositionsY,
      vehicleInspections: vehicleInspectionsImagens.vehicleInspections,
    });

    this.vehicleInspectionsSharedCollection = this.vehicleInspectionsService.addVehicleInspectionsToCollectionIfMissing(
      this.vehicleInspectionsSharedCollection,
      vehicleInspectionsImagens.vehicleInspections
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vehicleInspectionsService
      .query()
      .pipe(map((res: HttpResponse<IVehicleInspections[]>) => res.body ?? []))
      .pipe(
        map((vehicleInspections: IVehicleInspections[]) =>
          this.vehicleInspectionsService.addVehicleInspectionsToCollectionIfMissing(
            vehicleInspections,
            this.editForm.get('vehicleInspections')!.value
          )
        )
      )
      .subscribe((vehicleInspections: IVehicleInspections[]) => (this.vehicleInspectionsSharedCollection = vehicleInspections));
  }

  protected createFromForm(): IVehicleInspectionsImagens {
    return {
      ...new VehicleInspectionsImagens(),
      id: this.editForm.get(['id'])!.value,
      vehicleInspectionsImagensPositions: this.editForm.get(['vehicleInspectionsImagensPositions'])!.value,
      vehicleInspectionsImagensStatus: this.editForm.get(['vehicleInspectionsImagensStatus'])!.value,
      vehicleInspectionsImagensObs: this.editForm.get(['vehicleInspectionsImagensObs'])!.value,
      vehicleInspectionsImagensPhotoContentType: this.editForm.get(['vehicleInspectionsImagensPhotoContentType'])!.value,
      vehicleInspectionsImagensPhoto: this.editForm.get(['vehicleInspectionsImagensPhoto'])!.value,
      vehicleInspectionsImagensPositionsX: this.editForm.get(['vehicleInspectionsImagensPositionsX'])!.value,
      vehicleInspectionsImagensPositionsY: this.editForm.get(['vehicleInspectionsImagensPositionsY'])!.value,
      vehicleInspections: this.editForm.get(['vehicleInspections'])!.value,
    };
  }
}
