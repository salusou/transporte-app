import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicleControlItem, VehicleControlItem } from '../vehicle-control-item.model';
import { VehicleControlItemService } from '../service/vehicle-control-item.service';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';

@Component({
  selector: 'jhi-vehicle-control-item-update',
  templateUrl: './vehicle-control-item-update.component.html',
})
export class VehicleControlItemUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlsSharedCollection: IVehicleControls[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleControlStatus: [null, [Validators.required]],
    vehicleControlItemPlate: [null, [Validators.required]],
    vehicleControlItemType: [null, [Validators.required]],
    vehicleControlItemFipeCode: [],
    vehicleControlItemYear: [],
    vehicleControlItemFuel: [],
    vehicleControlItemBranch: [],
    vehicleControlItemModel: [],
    vehicleControlItemFuelAbbreviation: [],
    vehicleControlItemReferenceMonth: [],
    vehicleControlItemValue: [null, [Validators.required]],
    vehicleControlItemShippingValue: [null, [Validators.required]],
    vehicleControlItemCTE: [],
    vehicleControlItemCTEDate: [],
    vehicleControls: [null, Validators.required],
  });

  constructor(
    protected vehicleControlItemService: VehicleControlItemService,
    protected vehicleControlsService: VehicleControlsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlItem }) => {
      this.updateForm(vehicleControlItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleControlItem = this.createFromForm();
    if (vehicleControlItem.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleControlItemService.update(vehicleControlItem));
    } else {
      this.subscribeToSaveResponse(this.vehicleControlItemService.create(vehicleControlItem));
    }
  }

  trackVehicleControlsById(index: number, item: IVehicleControls): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleControlItem>>): void {
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

  protected updateForm(vehicleControlItem: IVehicleControlItem): void {
    this.editForm.patchValue({
      id: vehicleControlItem.id,
      vehicleControlStatus: vehicleControlItem.vehicleControlStatus,
      vehicleControlItemPlate: vehicleControlItem.vehicleControlItemPlate,
      vehicleControlItemType: vehicleControlItem.vehicleControlItemType,
      vehicleControlItemFipeCode: vehicleControlItem.vehicleControlItemFipeCode,
      vehicleControlItemYear: vehicleControlItem.vehicleControlItemYear,
      vehicleControlItemFuel: vehicleControlItem.vehicleControlItemFuel,
      vehicleControlItemBranch: vehicleControlItem.vehicleControlItemBranch,
      vehicleControlItemModel: vehicleControlItem.vehicleControlItemModel,
      vehicleControlItemFuelAbbreviation: vehicleControlItem.vehicleControlItemFuelAbbreviation,
      vehicleControlItemReferenceMonth: vehicleControlItem.vehicleControlItemReferenceMonth,
      vehicleControlItemValue: vehicleControlItem.vehicleControlItemValue,
      vehicleControlItemShippingValue: vehicleControlItem.vehicleControlItemShippingValue,
      vehicleControlItemCTE: vehicleControlItem.vehicleControlItemCTE,
      vehicleControlItemCTEDate: vehicleControlItem.vehicleControlItemCTEDate,
      vehicleControls: vehicleControlItem.vehicleControls,
    });

    this.vehicleControlsSharedCollection = this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(
      this.vehicleControlsSharedCollection,
      vehicleControlItem.vehicleControls
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

  protected createFromForm(): IVehicleControlItem {
    return {
      ...new VehicleControlItem(),
      id: this.editForm.get(['id'])!.value,
      vehicleControlStatus: this.editForm.get(['vehicleControlStatus'])!.value,
      vehicleControlItemPlate: this.editForm.get(['vehicleControlItemPlate'])!.value,
      vehicleControlItemType: this.editForm.get(['vehicleControlItemType'])!.value,
      vehicleControlItemFipeCode: this.editForm.get(['vehicleControlItemFipeCode'])!.value,
      vehicleControlItemYear: this.editForm.get(['vehicleControlItemYear'])!.value,
      vehicleControlItemFuel: this.editForm.get(['vehicleControlItemFuel'])!.value,
      vehicleControlItemBranch: this.editForm.get(['vehicleControlItemBranch'])!.value,
      vehicleControlItemModel: this.editForm.get(['vehicleControlItemModel'])!.value,
      vehicleControlItemFuelAbbreviation: this.editForm.get(['vehicleControlItemFuelAbbreviation'])!.value,
      vehicleControlItemReferenceMonth: this.editForm.get(['vehicleControlItemReferenceMonth'])!.value,
      vehicleControlItemValue: this.editForm.get(['vehicleControlItemValue'])!.value,
      vehicleControlItemShippingValue: this.editForm.get(['vehicleControlItemShippingValue'])!.value,
      vehicleControlItemCTE: this.editForm.get(['vehicleControlItemCTE'])!.value,
      vehicleControlItemCTEDate: this.editForm.get(['vehicleControlItemCTEDate'])!.value,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
    };
  }
}
