import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IParkingSector, ParkingSector } from '../parking-sector.model';
import { ParkingSectorService } from '../service/parking-sector.service';
import { IParking } from 'app/entities/parking/parking.model';
import { ParkingService } from 'app/entities/parking/service/parking.service';

@Component({
  selector: 'jhi-parking-sector-update',
  templateUrl: './parking-sector-update.component.html',
})
export class ParkingSectorUpdateComponent implements OnInit {
  isSaving = false;

  parkingsSharedCollection: IParking[] = [];

  editForm = this.fb.group({
    id: [],
    active: [null, [Validators.required]],
    sectorName: [null, [Validators.required]],
    parkingSpace: [null, [Validators.required]],
    parkingNumbersBegin: [],
    parkingNumbersFinal: [],
    parking: [null, Validators.required],
  });

  constructor(
    protected parkingSectorService: ParkingSectorService,
    protected parkingService: ParkingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parkingSector }) => {
      this.updateForm(parkingSector);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parkingSector = this.createFromForm();
    if (parkingSector.id !== undefined) {
      this.subscribeToSaveResponse(this.parkingSectorService.update(parkingSector));
    } else {
      this.subscribeToSaveResponse(this.parkingSectorService.create(parkingSector));
    }
  }

  trackParkingById(index: number, item: IParking): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParkingSector>>): void {
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

  protected updateForm(parkingSector: IParkingSector): void {
    this.editForm.patchValue({
      id: parkingSector.id,
      active: parkingSector.active,
      sectorName: parkingSector.sectorName,
      parkingSpace: parkingSector.parkingSpace,
      parkingNumbersBegin: parkingSector.parkingNumbersBegin,
      parkingNumbersFinal: parkingSector.parkingNumbersFinal,
      parking: parkingSector.parking,
    });

    this.parkingsSharedCollection = this.parkingService.addParkingToCollectionIfMissing(
      this.parkingsSharedCollection,
      parkingSector.parking
    );
  }

  protected loadRelationshipsOptions(): void {
    this.parkingService
      .query()
      .pipe(map((res: HttpResponse<IParking[]>) => res.body ?? []))
      .pipe(
        map((parkings: IParking[]) => this.parkingService.addParkingToCollectionIfMissing(parkings, this.editForm.get('parking')!.value))
      )
      .subscribe((parkings: IParking[]) => (this.parkingsSharedCollection = parkings));
  }

  protected createFromForm(): IParkingSector {
    return {
      ...new ParkingSector(),
      id: this.editForm.get(['id'])!.value,
      active: this.editForm.get(['active'])!.value,
      sectorName: this.editForm.get(['sectorName'])!.value,
      parkingSpace: this.editForm.get(['parkingSpace'])!.value,
      parkingNumbersBegin: this.editForm.get(['parkingNumbersBegin'])!.value,
      parkingNumbersFinal: this.editForm.get(['parkingNumbersFinal'])!.value,
      parking: this.editForm.get(['parking'])!.value,
    };
  }
}
