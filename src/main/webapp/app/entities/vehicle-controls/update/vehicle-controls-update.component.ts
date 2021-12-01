import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVehicleControls, VehicleControls } from '../vehicle-controls.model';
import { VehicleControlsService } from '../service/vehicle-controls.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';
import { CustomersGroupsService } from 'app/entities/customers-groups/service/customers-groups.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IStatus } from 'app/entities/status/status.model';
import { StatusService } from 'app/entities/status/service/status.service';

@Component({
  selector: 'jhi-vehicle-controls-update',
  templateUrl: './vehicle-controls-update.component.html',
  styleUrls: ['../../entities-styles.scss'],
})
export class VehicleControlsUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];
  customersSharedCollection: ICustomers[] = [];
  customersGroupsSharedCollection: ICustomersGroups[] = [];
  employeesSharedCollection: IEmployees[] = [];
  citiesSharedCollection: ICities[] = [];
  statusesSharedCollection: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    vehicleControlAuthorizedOrder: [],
    vehicleControlRequest: [],
    vehicleControlSinister: [],
    vehicleControlDate: [null, [Validators.required]],
    vehicleControlKm: [],
    vehicleControlPlate: [null, [Validators.maxLength(10)]],
    vehicleControlAmount: [],
    vehicleControlPrice: [],
    vehicleControlMaximumDeliveryDate: [],
    vehicleControlCollectionForecast: [],
    vehicleControlCollectionDeliveryForecast: [],
    vehicleControlDateCollected: [],
    vehicleControlDeliveryDate: [],
    affiliates: [null, Validators.required],
    customers: [null, Validators.required],
    customersGroups: [null, Validators.required],
    employees: [null, Validators.required],
    origin: [null, Validators.required],
    destination: [null, Validators.required],
    status: [null, Validators.required],
  });

  constructor(
    protected vehicleControlsService: VehicleControlsService,
    protected affiliatesService: AffiliatesService,
    protected customersService: CustomersService,
    protected customersGroupsService: CustomersGroupsService,
    protected employeesService: EmployeesService,
    protected citiesService: CitiesService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControls }) => {
      this.updateForm(vehicleControls);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleControls = this.createFromForm();
    if (vehicleControls.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleControlsService.update(vehicleControls));
    } else {
      this.subscribeToSaveResponse(this.vehicleControlsService.create(vehicleControls));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackCustomersById(index: number, item: ICustomers): number {
    return item.id!;
  }

  trackCustomersGroupsById(index: number, item: ICustomersGroups): number {
    return item.id!;
  }

  trackEmployeesById(index: number, item: IEmployees): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackStatusById(index: number, item: IStatus): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleControls>>): void {
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

  protected updateForm(vehicleControls: IVehicleControls): void {
    this.editForm.patchValue({
      id: vehicleControls.id,
      vehicleControlAuthorizedOrder: vehicleControls.vehicleControlAuthorizedOrder,
      vehicleControlRequest: vehicleControls.vehicleControlRequest,
      vehicleControlSinister: vehicleControls.vehicleControlSinister,
      vehicleControlDate: vehicleControls.vehicleControlDate,
      vehicleControlKm: vehicleControls.vehicleControlKm,
      vehicleControlPlate: vehicleControls.vehicleControlPlate,
      vehicleControlAmount: vehicleControls.vehicleControlAmount,
      vehicleControlPrice: vehicleControls.vehicleControlPrice,
      vehicleControlMaximumDeliveryDate: vehicleControls.vehicleControlMaximumDeliveryDate,
      vehicleControlCollectionForecast: vehicleControls.vehicleControlCollectionForecast,
      vehicleControlCollectionDeliveryForecast: vehicleControls.vehicleControlCollectionDeliveryForecast,
      vehicleControlDateCollected: vehicleControls.vehicleControlDateCollected,
      vehicleControlDeliveryDate: vehicleControls.vehicleControlDeliveryDate,
      affiliates: vehicleControls.affiliates,
      customers: vehicleControls.customers,
      customersGroups: vehicleControls.customersGroups,
      employees: vehicleControls.employees,
      origin: vehicleControls.origin,
      destination: vehicleControls.destination,
      status: vehicleControls.status,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      vehicleControls.affiliates
    );
    this.customersSharedCollection = this.customersService.addCustomersToCollectionIfMissing(
      this.customersSharedCollection,
      vehicleControls.customers
    );
    this.customersGroupsSharedCollection = this.customersGroupsService.addCustomersGroupsToCollectionIfMissing(
      this.customersGroupsSharedCollection,
      vehicleControls.customersGroups
    );
    this.employeesSharedCollection = this.employeesService.addEmployeesToCollectionIfMissing(
      this.employeesSharedCollection,
      vehicleControls.employees
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(
      this.citiesSharedCollection,
      vehicleControls.origin,
      vehicleControls.destination
    );
    this.statusesSharedCollection = this.statusService.addStatusToCollectionIfMissing(
      this.statusesSharedCollection,
      vehicleControls.status
    );
  }

  protected loadRelationshipsOptions(): void {
    this.affiliatesService
      .query()
      .pipe(map((res: HttpResponse<IAffiliates[]>) => res.body ?? []))
      .pipe(
        map((affiliates: IAffiliates[]) =>
          this.affiliatesService.addAffiliatesToCollectionIfMissing(affiliates, this.editForm.get('affiliates')!.value)
        )
      )
      .subscribe((affiliates: IAffiliates[]) => (this.affiliatesSharedCollection = affiliates));

    this.customersService
      .query()
      .pipe(map((res: HttpResponse<ICustomers[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomers[]) =>
          this.customersService.addCustomersToCollectionIfMissing(customers, this.editForm.get('customers')!.value)
        )
      )
      .subscribe((customers: ICustomers[]) => (this.customersSharedCollection = customers));

    this.customersGroupsService
      .query()
      .pipe(map((res: HttpResponse<ICustomersGroups[]>) => res.body ?? []))
      .pipe(
        map((customersGroups: ICustomersGroups[]) =>
          this.customersGroupsService.addCustomersGroupsToCollectionIfMissing(customersGroups, this.editForm.get('customersGroups')!.value)
        )
      )
      .subscribe((customersGroups: ICustomersGroups[]) => (this.customersGroupsSharedCollection = customersGroups));

    this.employeesService
      .query()
      .pipe(map((res: HttpResponse<IEmployees[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployees[]) =>
          this.employeesService.addEmployeesToCollectionIfMissing(employees, this.editForm.get('employees')!.value)
        )
      )
      .subscribe((employees: IEmployees[]) => (this.employeesSharedCollection = employees));

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

    this.statusService
      .query()
      .pipe(map((res: HttpResponse<IStatus[]>) => res.body ?? []))
      .pipe(map((statuses: IStatus[]) => this.statusService.addStatusToCollectionIfMissing(statuses, this.editForm.get('status')!.value)))
      .subscribe((statuses: IStatus[]) => (this.statusesSharedCollection = statuses));
  }

  protected createFromForm(): IVehicleControls {
    return {
      ...new VehicleControls(),
      id: this.editForm.get(['id'])!.value,
      vehicleControlAuthorizedOrder: this.editForm.get(['vehicleControlAuthorizedOrder'])!.value,
      vehicleControlRequest: this.editForm.get(['vehicleControlRequest'])!.value,
      vehicleControlSinister: this.editForm.get(['vehicleControlSinister'])!.value,
      vehicleControlDate: this.editForm.get(['vehicleControlDate'])!.value,
      vehicleControlKm: this.editForm.get(['vehicleControlKm'])!.value,
      vehicleControlPlate: this.editForm.get(['vehicleControlPlate'])!.value,
      vehicleControlAmount: this.editForm.get(['vehicleControlAmount'])!.value,
      vehicleControlPrice: this.editForm.get(['vehicleControlPrice'])!.value,
      vehicleControlMaximumDeliveryDate: this.editForm.get(['vehicleControlMaximumDeliveryDate'])!.value,
      vehicleControlCollectionForecast: this.editForm.get(['vehicleControlCollectionForecast'])!.value,
      vehicleControlCollectionDeliveryForecast: this.editForm.get(['vehicleControlCollectionDeliveryForecast'])!.value,
      vehicleControlDateCollected: this.editForm.get(['vehicleControlDateCollected'])!.value,
      vehicleControlDeliveryDate: this.editForm.get(['vehicleControlDeliveryDate'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      customers: this.editForm.get(['customers'])!.value,
      customersGroups: this.editForm.get(['customersGroups'])!.value,
      employees: this.editForm.get(['employees'])!.value,
      origin: this.editForm.get(['origin'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
