import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IParkingSectorSpace, ParkingSectorSpace } from '../parking-sector-space.model';
import { ParkingSectorSpaceService } from '../service/parking-sector-space.service';
import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { ParkingSectorService } from 'app/entities/parking-sector/service/parking-sector.service';

@Component({
  selector: 'jhi-parking-sector-space-update',
  templateUrl: './parking-sector-space-update.component.html',
})
export class ParkingSectorSpaceUpdateComponent implements OnInit {
  isSaving = false;

  parkingSectorsSharedCollection: IParkingSector[] = [];

  editForm = this.fb.group({
    id: [],
    parkingNumber: [null, [Validators.required]],
    parkingStatus: [null, [Validators.required]],
    parkingEntryDate: [],
    parkingDepartureDate: [],
    parkingHousingItemId: [],
    parkingSector: [null, Validators.required],
  });

  constructor(
    protected parkingSectorSpaceService: ParkingSectorSpaceService,
    protected parkingSectorService: ParkingSectorService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parkingSectorSpace }) => {
      this.updateForm(parkingSectorSpace);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parkingSectorSpace = this.createFromForm();
    if (parkingSectorSpace.id !== undefined) {
      this.subscribeToSaveResponse(this.parkingSectorSpaceService.update(parkingSectorSpace));
    } else {
      this.subscribeToSaveResponse(this.parkingSectorSpaceService.create(parkingSectorSpace));
    }
  }

  trackParkingSectorById(index: number, item: IParkingSector): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParkingSectorSpace>>): void {
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

  protected updateForm(parkingSectorSpace: IParkingSectorSpace): void {
    this.editForm.patchValue({
      id: parkingSectorSpace.id,
      parkingNumber: parkingSectorSpace.parkingNumber,
      parkingStatus: parkingSectorSpace.parkingStatus,
      parkingEntryDate: parkingSectorSpace.parkingEntryDate,
      parkingDepartureDate: parkingSectorSpace.parkingDepartureDate,
      parkingHousingItemId: parkingSectorSpace.parkingHousingItemId,
      parkingSector: parkingSectorSpace.parkingSector,
    });

    this.parkingSectorsSharedCollection = this.parkingSectorService.addParkingSectorToCollectionIfMissing(
      this.parkingSectorsSharedCollection,
      parkingSectorSpace.parkingSector
    );
  }

  protected loadRelationshipsOptions(): void {
    this.parkingSectorService
      .query()
      .pipe(map((res: HttpResponse<IParkingSector[]>) => res.body ?? []))
      .pipe(
        map((parkingSectors: IParkingSector[]) =>
          this.parkingSectorService.addParkingSectorToCollectionIfMissing(parkingSectors, this.editForm.get('parkingSector')!.value)
        )
      )
      .subscribe((parkingSectors: IParkingSector[]) => (this.parkingSectorsSharedCollection = parkingSectors));
  }

  protected createFromForm(): IParkingSectorSpace {
    return {
      ...new ParkingSectorSpace(),
      id: this.editForm.get(['id'])!.value,
      parkingNumber: this.editForm.get(['parkingNumber'])!.value,
      parkingStatus: this.editForm.get(['parkingStatus'])!.value,
      parkingEntryDate: this.editForm.get(['parkingEntryDate'])!.value,
      parkingDepartureDate: this.editForm.get(['parkingDepartureDate'])!.value,
      parkingHousingItemId: this.editForm.get(['parkingHousingItemId'])!.value,
      parkingSector: this.editForm.get(['parkingSector'])!.value,
    };
  }
}
