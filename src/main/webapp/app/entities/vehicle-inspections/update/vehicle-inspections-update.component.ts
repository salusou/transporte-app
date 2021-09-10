import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVehicleInspections, VehicleInspections } from '../vehicle-inspections.model';
import { VehicleInspectionsService } from '../service/vehicle-inspections.service';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { VehicleControlItemService } from 'app/entities/vehicle-control-item/service/vehicle-control-item.service';

@Component({
  selector: 'jhi-vehicle-inspections-update',
  templateUrl: './vehicle-inspections-update.component.html',
})
export class VehicleInspectionsUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlItemsSharedCollection: IVehicleControlItem[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleInspectionDate: [null, [Validators.required]],
    vehicleInspectionStatus: [null, [Validators.required]],
    vehicleInspectionModel: [null, [Validators.required]],
    vehicleInspectionLicensePlate: [null, [Validators.required]],
    vehicleInspectionKm: [],
    vehicleInspectionLicenseYear: [],
    vehicleInspectionHasManual: [],
    vehicleInspectionHasExtraKey: [],
    vehicleInspectionHasStickers: [],
    vehicleInspectionGas: [],
    vehicleInspectionRearView: [],
    vehicleInspectionHorn: [],
    vehicleInspectionWindshieldWiper: [],
    vehicleInspectionSquirt: [],
    vehicleInspectionInternalLight: [],
    vehicleInspectionPanelLight: [],
    vehicleInspectionHighLight: [],
    vehicleInspectionLowLight: [],
    vehicleInspectionTaillight: [],
    vehicleInspectionIndicator: [],
    vehicleInspectionBeacons: [],
    vehicleInspectionBreakLight: [],
    vehicleInspectionPlateLight: [],
    vehicleInspectionSpeedometer: [],
    vehicleInspectionTemperature: [],
    vehicleInspectionTires: [],
    vehicleInspectionStep: [],
    vehicleInspectionFireExtinguisher: [],
    vehicleInspectionSeatBelts: [],
    vehicleInspectionMonkey: [],
    vehicleInspectionTireIron: [],
    vehicleInspectionRadiatorCap: [],
    vehicleInspectionTriangle: [],
    vehicleInspectionServiceBrake: [],
    vehicleInspectionParkingBrake: [],
    vehicleInspectionOilLeaks: [],
    vehicleInspectionGlassActuator: [],
    vehicleInspectionVehicleCleaning: [],
    vehicleInspectionSeatState: [],
    vehicleInspectionExhausts: [],
    vehicleInspectionsObs: [null, [Validators.maxLength(500)]],
    vehicleInspectionsSignedUrl: [],
    vehicleControls: [],
  });

  constructor(
    protected vehicleInspectionsService: VehicleInspectionsService,
    protected vehicleControlItemService: VehicleControlItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleInspections }) => {
      if (vehicleInspections.id === undefined) {
        const today = dayjs().startOf('day');
        vehicleInspections.vehicleInspectionDate = today;
      }

      this.updateForm(vehicleInspections);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleInspections = this.createFromForm();
    if (vehicleInspections.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleInspectionsService.update(vehicleInspections));
    } else {
      this.subscribeToSaveResponse(this.vehicleInspectionsService.create(vehicleInspections));
    }
  }

  trackVehicleControlItemById(index: number, item: IVehicleControlItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleInspections>>): void {
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

  protected updateForm(vehicleInspections: IVehicleInspections): void {
    this.editForm.patchValue({
      id: vehicleInspections.id,
      vehicleInspectionDate: vehicleInspections.vehicleInspectionDate
        ? vehicleInspections.vehicleInspectionDate.format(DATE_TIME_FORMAT)
        : null,
      vehicleInspectionStatus: vehicleInspections.vehicleInspectionStatus,
      vehicleInspectionModel: vehicleInspections.vehicleInspectionModel,
      vehicleInspectionLicensePlate: vehicleInspections.vehicleInspectionLicensePlate,
      vehicleInspectionKm: vehicleInspections.vehicleInspectionKm,
      vehicleInspectionLicenseYear: vehicleInspections.vehicleInspectionLicenseYear,
      vehicleInspectionHasManual: vehicleInspections.vehicleInspectionHasManual,
      vehicleInspectionHasExtraKey: vehicleInspections.vehicleInspectionHasExtraKey,
      vehicleInspectionHasStickers: vehicleInspections.vehicleInspectionHasStickers,
      vehicleInspectionGas: vehicleInspections.vehicleInspectionGas,
      vehicleInspectionRearView: vehicleInspections.vehicleInspectionRearView,
      vehicleInspectionHorn: vehicleInspections.vehicleInspectionHorn,
      vehicleInspectionWindshieldWiper: vehicleInspections.vehicleInspectionWindshieldWiper,
      vehicleInspectionSquirt: vehicleInspections.vehicleInspectionSquirt,
      vehicleInspectionInternalLight: vehicleInspections.vehicleInspectionInternalLight,
      vehicleInspectionPanelLight: vehicleInspections.vehicleInspectionPanelLight,
      vehicleInspectionHighLight: vehicleInspections.vehicleInspectionHighLight,
      vehicleInspectionLowLight: vehicleInspections.vehicleInspectionLowLight,
      vehicleInspectionTaillight: vehicleInspections.vehicleInspectionTaillight,
      vehicleInspectionIndicator: vehicleInspections.vehicleInspectionIndicator,
      vehicleInspectionBeacons: vehicleInspections.vehicleInspectionBeacons,
      vehicleInspectionBreakLight: vehicleInspections.vehicleInspectionBreakLight,
      vehicleInspectionPlateLight: vehicleInspections.vehicleInspectionPlateLight,
      vehicleInspectionSpeedometer: vehicleInspections.vehicleInspectionSpeedometer,
      vehicleInspectionTemperature: vehicleInspections.vehicleInspectionTemperature,
      vehicleInspectionTires: vehicleInspections.vehicleInspectionTires,
      vehicleInspectionStep: vehicleInspections.vehicleInspectionStep,
      vehicleInspectionFireExtinguisher: vehicleInspections.vehicleInspectionFireExtinguisher,
      vehicleInspectionSeatBelts: vehicleInspections.vehicleInspectionSeatBelts,
      vehicleInspectionMonkey: vehicleInspections.vehicleInspectionMonkey,
      vehicleInspectionTireIron: vehicleInspections.vehicleInspectionTireIron,
      vehicleInspectionRadiatorCap: vehicleInspections.vehicleInspectionRadiatorCap,
      vehicleInspectionTriangle: vehicleInspections.vehicleInspectionTriangle,
      vehicleInspectionServiceBrake: vehicleInspections.vehicleInspectionServiceBrake,
      vehicleInspectionParkingBrake: vehicleInspections.vehicleInspectionParkingBrake,
      vehicleInspectionOilLeaks: vehicleInspections.vehicleInspectionOilLeaks,
      vehicleInspectionGlassActuator: vehicleInspections.vehicleInspectionGlassActuator,
      vehicleInspectionVehicleCleaning: vehicleInspections.vehicleInspectionVehicleCleaning,
      vehicleInspectionSeatState: vehicleInspections.vehicleInspectionSeatState,
      vehicleInspectionExhausts: vehicleInspections.vehicleInspectionExhausts,
      vehicleInspectionsObs: vehicleInspections.vehicleInspectionsObs,
      vehicleInspectionsSignedUrl: vehicleInspections.vehicleInspectionsSignedUrl,
      vehicleControls: vehicleInspections.vehicleControls,
    });

    this.vehicleControlItemsSharedCollection = this.vehicleControlItemService.addVehicleControlItemToCollectionIfMissing(
      this.vehicleControlItemsSharedCollection,
      vehicleInspections.vehicleControls
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vehicleControlItemService
      .query()
      .pipe(map((res: HttpResponse<IVehicleControlItem[]>) => res.body ?? []))
      .pipe(
        map((vehicleControlItems: IVehicleControlItem[]) =>
          this.vehicleControlItemService.addVehicleControlItemToCollectionIfMissing(
            vehicleControlItems,
            this.editForm.get('vehicleControls')!.value
          )
        )
      )
      .subscribe((vehicleControlItems: IVehicleControlItem[]) => (this.vehicleControlItemsSharedCollection = vehicleControlItems));
  }

  protected createFromForm(): IVehicleInspections {
    return {
      ...new VehicleInspections(),
      id: this.editForm.get(['id'])!.value,
      vehicleInspectionDate: this.editForm.get(['vehicleInspectionDate'])!.value
        ? dayjs(this.editForm.get(['vehicleInspectionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      vehicleInspectionStatus: this.editForm.get(['vehicleInspectionStatus'])!.value,
      vehicleInspectionModel: this.editForm.get(['vehicleInspectionModel'])!.value,
      vehicleInspectionLicensePlate: this.editForm.get(['vehicleInspectionLicensePlate'])!.value,
      vehicleInspectionKm: this.editForm.get(['vehicleInspectionKm'])!.value,
      vehicleInspectionLicenseYear: this.editForm.get(['vehicleInspectionLicenseYear'])!.value,
      vehicleInspectionHasManual: this.editForm.get(['vehicleInspectionHasManual'])!.value,
      vehicleInspectionHasExtraKey: this.editForm.get(['vehicleInspectionHasExtraKey'])!.value,
      vehicleInspectionHasStickers: this.editForm.get(['vehicleInspectionHasStickers'])!.value,
      vehicleInspectionGas: this.editForm.get(['vehicleInspectionGas'])!.value,
      vehicleInspectionRearView: this.editForm.get(['vehicleInspectionRearView'])!.value,
      vehicleInspectionHorn: this.editForm.get(['vehicleInspectionHorn'])!.value,
      vehicleInspectionWindshieldWiper: this.editForm.get(['vehicleInspectionWindshieldWiper'])!.value,
      vehicleInspectionSquirt: this.editForm.get(['vehicleInspectionSquirt'])!.value,
      vehicleInspectionInternalLight: this.editForm.get(['vehicleInspectionInternalLight'])!.value,
      vehicleInspectionPanelLight: this.editForm.get(['vehicleInspectionPanelLight'])!.value,
      vehicleInspectionHighLight: this.editForm.get(['vehicleInspectionHighLight'])!.value,
      vehicleInspectionLowLight: this.editForm.get(['vehicleInspectionLowLight'])!.value,
      vehicleInspectionTaillight: this.editForm.get(['vehicleInspectionTaillight'])!.value,
      vehicleInspectionIndicator: this.editForm.get(['vehicleInspectionIndicator'])!.value,
      vehicleInspectionBeacons: this.editForm.get(['vehicleInspectionBeacons'])!.value,
      vehicleInspectionBreakLight: this.editForm.get(['vehicleInspectionBreakLight'])!.value,
      vehicleInspectionPlateLight: this.editForm.get(['vehicleInspectionPlateLight'])!.value,
      vehicleInspectionSpeedometer: this.editForm.get(['vehicleInspectionSpeedometer'])!.value,
      vehicleInspectionTemperature: this.editForm.get(['vehicleInspectionTemperature'])!.value,
      vehicleInspectionTires: this.editForm.get(['vehicleInspectionTires'])!.value,
      vehicleInspectionStep: this.editForm.get(['vehicleInspectionStep'])!.value,
      vehicleInspectionFireExtinguisher: this.editForm.get(['vehicleInspectionFireExtinguisher'])!.value,
      vehicleInspectionSeatBelts: this.editForm.get(['vehicleInspectionSeatBelts'])!.value,
      vehicleInspectionMonkey: this.editForm.get(['vehicleInspectionMonkey'])!.value,
      vehicleInspectionTireIron: this.editForm.get(['vehicleInspectionTireIron'])!.value,
      vehicleInspectionRadiatorCap: this.editForm.get(['vehicleInspectionRadiatorCap'])!.value,
      vehicleInspectionTriangle: this.editForm.get(['vehicleInspectionTriangle'])!.value,
      vehicleInspectionServiceBrake: this.editForm.get(['vehicleInspectionServiceBrake'])!.value,
      vehicleInspectionParkingBrake: this.editForm.get(['vehicleInspectionParkingBrake'])!.value,
      vehicleInspectionOilLeaks: this.editForm.get(['vehicleInspectionOilLeaks'])!.value,
      vehicleInspectionGlassActuator: this.editForm.get(['vehicleInspectionGlassActuator'])!.value,
      vehicleInspectionVehicleCleaning: this.editForm.get(['vehicleInspectionVehicleCleaning'])!.value,
      vehicleInspectionSeatState: this.editForm.get(['vehicleInspectionSeatState'])!.value,
      vehicleInspectionExhausts: this.editForm.get(['vehicleInspectionExhausts'])!.value,
      vehicleInspectionsObs: this.editForm.get(['vehicleInspectionsObs'])!.value,
      vehicleInspectionsSignedUrl: this.editForm.get(['vehicleInspectionsSignedUrl'])!.value,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
    };
  }
}
