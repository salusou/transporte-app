import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicleControlBilling, VehicleControlBilling } from '../vehicle-control-billing.model';
import { VehicleControlBillingService } from '../service/vehicle-control-billing.service';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { IFees } from 'app/entities/fees/fees.model';
import { FeesService } from 'app/entities/fees/service/fees.service';

@Component({
  selector: 'jhi-vehicle-control-billing-update',
  templateUrl: './vehicle-control-billing-update.component.html',
})
export class VehicleControlBillingUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlsSharedCollection: IVehicleControls[] = [];
  feesSharedCollection: IFees[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleControlBillingDate: [null, [Validators.required]],
    vehicleControlBillingExpirationDate: [],
    vehicleControlBillingPaymentDate: [],
    vehicleControlBillingSellerCommission: [],
    vehicleControlBillingDriverCommission: [],
    vehicleControlBillingAmount: [null, [Validators.required]],
    vehicleControlBillingTotalValue: [null, [Validators.required]],
    vehicleControlBillingInsuranceDiscount: [],
    vehicleControlBillingCustomerBank: [],
    vehicleControlBillingAnticipate: [],
    vehicleControlBillingManifest: [],
    vehicleControls: [null, Validators.required],
    fees: [null, Validators.required],
  });

  constructor(
    protected vehicleControlBillingService: VehicleControlBillingService,
    protected vehicleControlsService: VehicleControlsService,
    protected feesService: FeesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlBilling }) => {
      this.updateForm(vehicleControlBilling);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleControlBilling = this.createFromForm();
    if (vehicleControlBilling.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleControlBillingService.update(vehicleControlBilling));
    } else {
      this.subscribeToSaveResponse(this.vehicleControlBillingService.create(vehicleControlBilling));
    }
  }

  trackVehicleControlsById(index: number, item: IVehicleControls): number {
    return item.id!;
  }

  trackFeesById(index: number, item: IFees): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleControlBilling>>): void {
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

  protected updateForm(vehicleControlBilling: IVehicleControlBilling): void {
    this.editForm.patchValue({
      id: vehicleControlBilling.id,
      vehicleControlBillingDate: vehicleControlBilling.vehicleControlBillingDate,
      vehicleControlBillingExpirationDate: vehicleControlBilling.vehicleControlBillingExpirationDate,
      vehicleControlBillingPaymentDate: vehicleControlBilling.vehicleControlBillingPaymentDate,
      vehicleControlBillingSellerCommission: vehicleControlBilling.vehicleControlBillingSellerCommission,
      vehicleControlBillingDriverCommission: vehicleControlBilling.vehicleControlBillingDriverCommission,
      vehicleControlBillingAmount: vehicleControlBilling.vehicleControlBillingAmount,
      vehicleControlBillingTotalValue: vehicleControlBilling.vehicleControlBillingTotalValue,
      vehicleControlBillingInsuranceDiscount: vehicleControlBilling.vehicleControlBillingInsuranceDiscount,
      vehicleControlBillingCustomerBank: vehicleControlBilling.vehicleControlBillingCustomerBank,
      vehicleControlBillingAnticipate: vehicleControlBilling.vehicleControlBillingAnticipate,
      vehicleControlBillingManifest: vehicleControlBilling.vehicleControlBillingManifest,
      vehicleControls: vehicleControlBilling.vehicleControls,
      fees: vehicleControlBilling.fees,
    });

    this.vehicleControlsSharedCollection = this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(
      this.vehicleControlsSharedCollection,
      vehicleControlBilling.vehicleControls
    );
    this.feesSharedCollection = this.feesService.addFeesToCollectionIfMissing(this.feesSharedCollection, vehicleControlBilling.fees);
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

    this.feesService
      .query()
      .pipe(map((res: HttpResponse<IFees[]>) => res.body ?? []))
      .pipe(map((fees: IFees[]) => this.feesService.addFeesToCollectionIfMissing(fees, this.editForm.get('fees')!.value)))
      .subscribe((fees: IFees[]) => (this.feesSharedCollection = fees));
  }

  protected createFromForm(): IVehicleControlBilling {
    return {
      ...new VehicleControlBilling(),
      id: this.editForm.get(['id'])!.value,
      vehicleControlBillingDate: this.editForm.get(['vehicleControlBillingDate'])!.value,
      vehicleControlBillingExpirationDate: this.editForm.get(['vehicleControlBillingExpirationDate'])!.value,
      vehicleControlBillingPaymentDate: this.editForm.get(['vehicleControlBillingPaymentDate'])!.value,
      vehicleControlBillingSellerCommission: this.editForm.get(['vehicleControlBillingSellerCommission'])!.value,
      vehicleControlBillingDriverCommission: this.editForm.get(['vehicleControlBillingDriverCommission'])!.value,
      vehicleControlBillingAmount: this.editForm.get(['vehicleControlBillingAmount'])!.value,
      vehicleControlBillingTotalValue: this.editForm.get(['vehicleControlBillingTotalValue'])!.value,
      vehicleControlBillingInsuranceDiscount: this.editForm.get(['vehicleControlBillingInsuranceDiscount'])!.value,
      vehicleControlBillingCustomerBank: this.editForm.get(['vehicleControlBillingCustomerBank'])!.value,
      vehicleControlBillingAnticipate: this.editForm.get(['vehicleControlBillingAnticipate'])!.value,
      vehicleControlBillingManifest: this.editForm.get(['vehicleControlBillingManifest'])!.value,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
      fees: this.editForm.get(['fees'])!.value,
    };
  }
}
