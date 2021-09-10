import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVehicleLocationStatus, VehicleLocationStatus } from '../vehicle-location-status.model';
import { VehicleLocationStatusService } from '../service/vehicle-location-status.service';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

@Component({
  selector: 'jhi-vehicle-location-status-update',
  templateUrl: './vehicle-location-status-update.component.html',
})
export class VehicleLocationStatusUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlsSharedCollection: IVehicleControls[] = [];
  citiesSharedCollection: ICities[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleLocationStatusDate: [null, [Validators.required]],
    vehicleLocationStatusDescription: [null, [Validators.required, Validators.maxLength(500)]],
    vehicleControls: [null, Validators.required],
    cities: [null, Validators.required],
  });

  constructor(
    protected vehicleLocationStatusService: VehicleLocationStatusService,
    protected vehicleControlsService: VehicleControlsService,
    protected citiesService: CitiesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleLocationStatus }) => {
      if (vehicleLocationStatus.id === undefined) {
        const today = dayjs().startOf('day');
        vehicleLocationStatus.vehicleLocationStatusDate = today;
      }

      this.updateForm(vehicleLocationStatus);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleLocationStatus = this.createFromForm();
    if (vehicleLocationStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleLocationStatusService.update(vehicleLocationStatus));
    } else {
      this.subscribeToSaveResponse(this.vehicleLocationStatusService.create(vehicleLocationStatus));
    }
  }

  trackVehicleControlsById(index: number, item: IVehicleControls): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleLocationStatus>>): void {
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

  protected updateForm(vehicleLocationStatus: IVehicleLocationStatus): void {
    this.editForm.patchValue({
      id: vehicleLocationStatus.id,
      vehicleLocationStatusDate: vehicleLocationStatus.vehicleLocationStatusDate
        ? vehicleLocationStatus.vehicleLocationStatusDate.format(DATE_TIME_FORMAT)
        : null,
      vehicleLocationStatusDescription: vehicleLocationStatus.vehicleLocationStatusDescription,
      vehicleControls: vehicleLocationStatus.vehicleControls,
      cities: vehicleLocationStatus.cities,
    });

    this.vehicleControlsSharedCollection = this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(
      this.vehicleControlsSharedCollection,
      vehicleLocationStatus.vehicleControls
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(
      this.citiesSharedCollection,
      vehicleLocationStatus.cities
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

    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing(cities, this.editForm.get('cities')!.value)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));
  }

  protected createFromForm(): IVehicleLocationStatus {
    return {
      ...new VehicleLocationStatus(),
      id: this.editForm.get(['id'])!.value,
      vehicleLocationStatusDate: this.editForm.get(['vehicleLocationStatusDate'])!.value
        ? dayjs(this.editForm.get(['vehicleLocationStatusDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      vehicleLocationStatusDescription: this.editForm.get(['vehicleLocationStatusDescription'])!.value,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
      cities: this.editForm.get(['cities'])!.value,
    };
  }
}
