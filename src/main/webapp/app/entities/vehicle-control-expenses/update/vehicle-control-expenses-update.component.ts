import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicleControlExpenses, VehicleControlExpenses } from '../vehicle-control-expenses.model';
import { VehicleControlExpensesService } from '../service/vehicle-control-expenses.service';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { VehicleControlsService } from 'app/entities/vehicle-controls/service/vehicle-controls.service';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { VehicleControlItemService } from 'app/entities/vehicle-control-item/service/vehicle-control-item.service';

@Component({
  selector: 'jhi-vehicle-control-expenses-update',
  templateUrl: './vehicle-control-expenses-update.component.html',
})
export class VehicleControlExpensesUpdateComponent implements OnInit {
  isSaving = false;

  vehicleControlsSharedCollection: IVehicleControls[] = [];
  suppliersSharedCollection: ISuppliers[] = [];
  citiesSharedCollection: ICities[] = [];
  vehicleControlItemsSharedCollection: IVehicleControlItem[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleControlExpensesDescription: [null, [Validators.required]],
    vehicleControlExpensesDriverType: [],
    vehicleControlExpensesPurchaseOrder: [],
    vehicleControlExpensesDueDate: [],
    vehicleControlExpensesPaymentDate: [],
    vehicleControlExpensesBillingTotalValue: [],
    vehicleControlExpensesDriverCommission: [],
    vehicleControls: [null, Validators.required],
    suppliers: [null, Validators.required],
    origin: [null, Validators.required],
    destination: [null, Validators.required],
    vehicleControlItem: [null, Validators.required],
  });

  constructor(
    protected vehicleControlExpensesService: VehicleControlExpensesService,
    protected vehicleControlsService: VehicleControlsService,
    protected suppliersService: SuppliersService,
    protected citiesService: CitiesService,
    protected vehicleControlItemService: VehicleControlItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlExpenses }) => {
      this.updateForm(vehicleControlExpenses);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleControlExpenses = this.createFromForm();
    if (vehicleControlExpenses.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleControlExpensesService.update(vehicleControlExpenses));
    } else {
      this.subscribeToSaveResponse(this.vehicleControlExpensesService.create(vehicleControlExpenses));
    }
  }

  trackVehicleControlsById(index: number, item: IVehicleControls): number {
    return item.id!;
  }

  trackSuppliersById(index: number, item: ISuppliers): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackVehicleControlItemById(index: number, item: IVehicleControlItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleControlExpenses>>): void {
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

  protected updateForm(vehicleControlExpenses: IVehicleControlExpenses): void {
    this.editForm.patchValue({
      id: vehicleControlExpenses.id,
      vehicleControlExpensesDescription: vehicleControlExpenses.vehicleControlExpensesDescription,
      vehicleControlExpensesDriverType: vehicleControlExpenses.vehicleControlExpensesDriverType,
      vehicleControlExpensesPurchaseOrder: vehicleControlExpenses.vehicleControlExpensesPurchaseOrder,
      vehicleControlExpensesDueDate: vehicleControlExpenses.vehicleControlExpensesDueDate,
      vehicleControlExpensesPaymentDate: vehicleControlExpenses.vehicleControlExpensesPaymentDate,
      vehicleControlExpensesBillingTotalValue: vehicleControlExpenses.vehicleControlExpensesBillingTotalValue,
      vehicleControlExpensesDriverCommission: vehicleControlExpenses.vehicleControlExpensesDriverCommission,
      vehicleControls: vehicleControlExpenses.vehicleControls,
      suppliers: vehicleControlExpenses.suppliers,
      origin: vehicleControlExpenses.origin,
      destination: vehicleControlExpenses.destination,
      vehicleControlItem: vehicleControlExpenses.vehicleControlItem,
    });

    this.vehicleControlsSharedCollection = this.vehicleControlsService.addVehicleControlsToCollectionIfMissing(
      this.vehicleControlsSharedCollection,
      vehicleControlExpenses.vehicleControls
    );
    this.suppliersSharedCollection = this.suppliersService.addSuppliersToCollectionIfMissing(
      this.suppliersSharedCollection,
      vehicleControlExpenses.suppliers
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(
      this.citiesSharedCollection,
      vehicleControlExpenses.origin,
      vehicleControlExpenses.destination
    );
    this.vehicleControlItemsSharedCollection = this.vehicleControlItemService.addVehicleControlItemToCollectionIfMissing(
      this.vehicleControlItemsSharedCollection,
      vehicleControlExpenses.vehicleControlItem
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

    this.suppliersService
      .query()
      .pipe(map((res: HttpResponse<ISuppliers[]>) => res.body ?? []))
      .pipe(
        map((suppliers: ISuppliers[]) =>
          this.suppliersService.addSuppliersToCollectionIfMissing(suppliers, this.editForm.get('suppliers')!.value)
        )
      )
      .subscribe((suppliers: ISuppliers[]) => (this.suppliersSharedCollection = suppliers));

    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(
        map((cities: ICities[]) =>
          this.citiesService.addCitiesToCollectionIfMissing(
            cities,
            this.editForm.get('origin')!.value,
            this.editForm.get('destination')!.value
          )
        )
      )
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));

    this.vehicleControlItemService
      .query()
      .pipe(map((res: HttpResponse<IVehicleControlItem[]>) => res.body ?? []))
      .pipe(
        map((vehicleControlItems: IVehicleControlItem[]) =>
          this.vehicleControlItemService.addVehicleControlItemToCollectionIfMissing(
            vehicleControlItems,
            this.editForm.get('vehicleControlItem')!.value
          )
        )
      )
      .subscribe((vehicleControlItems: IVehicleControlItem[]) => (this.vehicleControlItemsSharedCollection = vehicleControlItems));
  }

  protected createFromForm(): IVehicleControlExpenses {
    return {
      ...new VehicleControlExpenses(),
      id: this.editForm.get(['id'])!.value,
      vehicleControlExpensesDescription: this.editForm.get(['vehicleControlExpensesDescription'])!.value,
      vehicleControlExpensesDriverType: this.editForm.get(['vehicleControlExpensesDriverType'])!.value,
      vehicleControlExpensesPurchaseOrder: this.editForm.get(['vehicleControlExpensesPurchaseOrder'])!.value,
      vehicleControlExpensesDueDate: this.editForm.get(['vehicleControlExpensesDueDate'])!.value,
      vehicleControlExpensesPaymentDate: this.editForm.get(['vehicleControlExpensesPaymentDate'])!.value,
      vehicleControlExpensesBillingTotalValue: this.editForm.get(['vehicleControlExpensesBillingTotalValue'])!.value,
      vehicleControlExpensesDriverCommission: this.editForm.get(['vehicleControlExpensesDriverCommission'])!.value,
      vehicleControls: this.editForm.get(['vehicleControls'])!.value,
      suppliers: this.editForm.get(['suppliers'])!.value,
      origin: this.editForm.get(['origin'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      vehicleControlItem: this.editForm.get(['vehicleControlItem'])!.value,
    };
  }
}
