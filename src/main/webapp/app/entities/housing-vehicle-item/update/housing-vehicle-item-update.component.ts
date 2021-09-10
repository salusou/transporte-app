import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHousingVehicleItem, HousingVehicleItem } from '../housing-vehicle-item.model';
import { HousingVehicleItemService } from '../service/housing-vehicle-item.service';
import { IHousing } from 'app/entities/housing/housing.model';
import { HousingService } from 'app/entities/housing/service/housing.service';
import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { ParkingSectorService } from 'app/entities/parking-sector/service/parking-sector.service';
import { IParkingSectorSpace } from 'app/entities/parking-sector-space/parking-sector-space.model';
import { ParkingSectorSpaceService } from 'app/entities/parking-sector-space/service/parking-sector-space.service';

@Component({
  selector: 'jhi-housing-vehicle-item-update',
  templateUrl: './housing-vehicle-item-update.component.html',
})
export class HousingVehicleItemUpdateComponent implements OnInit {
  isSaving = false;

  housingsSharedCollection: IHousing[] = [];
  parkingSectorsSharedCollection: IParkingSector[] = [];
  parkingSectorSpacesSharedCollection: IParkingSectorSpace[] = [];

  editForm = this.fb.group({
    id: [],
    housingVehicleItemStatus: [null, [Validators.required]],
    housingVehicleItemPlate: [null, [Validators.required]],
    housingVehicleItemType: [null, [Validators.required]],
    housingVehicleItemFipeCode: [],
    housingVehicleItemYear: [],
    housingVehicleItemFuel: [],
    housingVehicleItemBranch: [],
    housingVehicleItemModel: [],
    housingVehicleItemFuelAbbreviation: [],
    housingVehicleItemReferenceMonth: [],
    housingVehicleItemValue: [null, [Validators.required]],
    housingVehicleItemShippingValue: [null, [Validators.required]],
    housing: [null, Validators.required],
    parkingSector: [],
    parkingSectorSpace: [],
  });

  constructor(
    protected housingVehicleItemService: HousingVehicleItemService,
    protected housingService: HousingService,
    protected parkingSectorService: ParkingSectorService,
    protected parkingSectorSpaceService: ParkingSectorSpaceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ housingVehicleItem }) => {
      this.updateForm(housingVehicleItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const housingVehicleItem = this.createFromForm();
    if (housingVehicleItem.id !== undefined) {
      this.subscribeToSaveResponse(this.housingVehicleItemService.update(housingVehicleItem));
    } else {
      this.subscribeToSaveResponse(this.housingVehicleItemService.create(housingVehicleItem));
    }
  }

  trackHousingById(index: number, item: IHousing): number {
    return item.id!;
  }

  trackParkingSectorById(index: number, item: IParkingSector): number {
    return item.id!;
  }

  trackParkingSectorSpaceById(index: number, item: IParkingSectorSpace): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHousingVehicleItem>>): void {
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

  protected updateForm(housingVehicleItem: IHousingVehicleItem): void {
    this.editForm.patchValue({
      id: housingVehicleItem.id,
      housingVehicleItemStatus: housingVehicleItem.housingVehicleItemStatus,
      housingVehicleItemPlate: housingVehicleItem.housingVehicleItemPlate,
      housingVehicleItemType: housingVehicleItem.housingVehicleItemType,
      housingVehicleItemFipeCode: housingVehicleItem.housingVehicleItemFipeCode,
      housingVehicleItemYear: housingVehicleItem.housingVehicleItemYear,
      housingVehicleItemFuel: housingVehicleItem.housingVehicleItemFuel,
      housingVehicleItemBranch: housingVehicleItem.housingVehicleItemBranch,
      housingVehicleItemModel: housingVehicleItem.housingVehicleItemModel,
      housingVehicleItemFuelAbbreviation: housingVehicleItem.housingVehicleItemFuelAbbreviation,
      housingVehicleItemReferenceMonth: housingVehicleItem.housingVehicleItemReferenceMonth,
      housingVehicleItemValue: housingVehicleItem.housingVehicleItemValue,
      housingVehicleItemShippingValue: housingVehicleItem.housingVehicleItemShippingValue,
      housing: housingVehicleItem.housing,
      parkingSector: housingVehicleItem.parkingSector,
      parkingSectorSpace: housingVehicleItem.parkingSectorSpace,
    });

    this.housingsSharedCollection = this.housingService.addHousingToCollectionIfMissing(
      this.housingsSharedCollection,
      housingVehicleItem.housing
    );
    this.parkingSectorsSharedCollection = this.parkingSectorService.addParkingSectorToCollectionIfMissing(
      this.parkingSectorsSharedCollection,
      housingVehicleItem.parkingSector
    );
    this.parkingSectorSpacesSharedCollection = this.parkingSectorSpaceService.addParkingSectorSpaceToCollectionIfMissing(
      this.parkingSectorSpacesSharedCollection,
      housingVehicleItem.parkingSectorSpace
    );
  }

  protected loadRelationshipsOptions(): void {
    this.housingService
      .query()
      .pipe(map((res: HttpResponse<IHousing[]>) => res.body ?? []))
      .pipe(
        map((housings: IHousing[]) => this.housingService.addHousingToCollectionIfMissing(housings, this.editForm.get('housing')!.value))
      )
      .subscribe((housings: IHousing[]) => (this.housingsSharedCollection = housings));

    this.parkingSectorService
      .query()
      .pipe(map((res: HttpResponse<IParkingSector[]>) => res.body ?? []))
      .pipe(
        map((parkingSectors: IParkingSector[]) =>
          this.parkingSectorService.addParkingSectorToCollectionIfMissing(parkingSectors, this.editForm.get('parkingSector')!.value)
        )
      )
      .subscribe((parkingSectors: IParkingSector[]) => (this.parkingSectorsSharedCollection = parkingSectors));

    this.parkingSectorSpaceService
      .query()
      .pipe(map((res: HttpResponse<IParkingSectorSpace[]>) => res.body ?? []))
      .pipe(
        map((parkingSectorSpaces: IParkingSectorSpace[]) =>
          this.parkingSectorSpaceService.addParkingSectorSpaceToCollectionIfMissing(
            parkingSectorSpaces,
            this.editForm.get('parkingSectorSpace')!.value
          )
        )
      )
      .subscribe((parkingSectorSpaces: IParkingSectorSpace[]) => (this.parkingSectorSpacesSharedCollection = parkingSectorSpaces));
  }

  protected createFromForm(): IHousingVehicleItem {
    return {
      ...new HousingVehicleItem(),
      id: this.editForm.get(['id'])!.value,
      housingVehicleItemStatus: this.editForm.get(['housingVehicleItemStatus'])!.value,
      housingVehicleItemPlate: this.editForm.get(['housingVehicleItemPlate'])!.value,
      housingVehicleItemType: this.editForm.get(['housingVehicleItemType'])!.value,
      housingVehicleItemFipeCode: this.editForm.get(['housingVehicleItemFipeCode'])!.value,
      housingVehicleItemYear: this.editForm.get(['housingVehicleItemYear'])!.value,
      housingVehicleItemFuel: this.editForm.get(['housingVehicleItemFuel'])!.value,
      housingVehicleItemBranch: this.editForm.get(['housingVehicleItemBranch'])!.value,
      housingVehicleItemModel: this.editForm.get(['housingVehicleItemModel'])!.value,
      housingVehicleItemFuelAbbreviation: this.editForm.get(['housingVehicleItemFuelAbbreviation'])!.value,
      housingVehicleItemReferenceMonth: this.editForm.get(['housingVehicleItemReferenceMonth'])!.value,
      housingVehicleItemValue: this.editForm.get(['housingVehicleItemValue'])!.value,
      housingVehicleItemShippingValue: this.editForm.get(['housingVehicleItemShippingValue'])!.value,
      housing: this.editForm.get(['housing'])!.value,
      parkingSector: this.editForm.get(['parkingSector'])!.value,
      parkingSectorSpace: this.editForm.get(['parkingSectorSpace'])!.value,
    };
  }
}
