import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHousing, Housing } from '../housing.model';
import { HousingService } from '../service/housing.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { IStatus } from 'app/entities/status/status.model';
import { StatusService } from 'app/entities/status/service/status.service';
import { ICustomers } from 'app/entities/customers/customers.model';
import { CustomersService } from 'app/entities/customers/service/customers.service';
import { IEmployees } from 'app/entities/employees/employees.model';
import { EmployeesService } from 'app/entities/employees/service/employees.service';
import { IParking } from 'app/entities/parking/parking.model';
import { ParkingService } from 'app/entities/parking/service/parking.service';
import { ICostCenter } from 'app/entities/cost-center/cost-center.model';
import { CostCenterService } from 'app/entities/cost-center/service/cost-center.service';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';

@Component({
  selector: 'jhi-housing-update',
  templateUrl: './housing-update.component.html',
})
export class HousingUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];
  statusesSharedCollection: IStatus[] = [];
  customersSharedCollection: ICustomers[] = [];
  employeesSharedCollection: IEmployees[] = [];
  parkingsSharedCollection: IParking[] = [];
  costCentersSharedCollection: ICostCenter[] = [];
  suppliersSharedCollection: ISuppliers[] = [];
  citiesSharedCollection: ICities[] = [];

  editForm = this.fb.group({
    id: [],
    housingDate: [null, [Validators.required]],
    housingEntranceDate: [null, [Validators.required]],
    housingExit: [],
    housingReceiptNumber: [],
    housingDailyPrice: [null, [Validators.required]],
    housingDescription: [null, [Validators.maxLength(500)]],
    affiliates: [null, Validators.required],
    status: [null, Validators.required],
    customers: [null, Validators.required],
    employees: [null, Validators.required],
    parking: [],
    costCenter: [],
    suppliers: [null, Validators.required],
    cities: [null, Validators.required],
  });

  constructor(
    protected housingService: HousingService,
    protected affiliatesService: AffiliatesService,
    protected statusService: StatusService,
    protected customersService: CustomersService,
    protected employeesService: EmployeesService,
    protected parkingService: ParkingService,
    protected costCenterService: CostCenterService,
    protected suppliersService: SuppliersService,
    protected citiesService: CitiesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ housing }) => {
      if (housing.id === undefined) {
        const today = dayjs().startOf('day');
        housing.housingEntranceDate = today;
        housing.housingExit = today;
      }

      this.updateForm(housing);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const housing = this.createFromForm();
    if (housing.id !== undefined) {
      this.subscribeToSaveResponse(this.housingService.update(housing));
    } else {
      this.subscribeToSaveResponse(this.housingService.create(housing));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackStatusById(index: number, item: IStatus): number {
    return item.id!;
  }

  trackCustomersById(index: number, item: ICustomers): number {
    return item.id!;
  }

  trackEmployeesById(index: number, item: IEmployees): number {
    return item.id!;
  }

  trackParkingById(index: number, item: IParking): number {
    return item.id!;
  }

  trackCostCenterById(index: number, item: ICostCenter): number {
    return item.id!;
  }

  trackSuppliersById(index: number, item: ISuppliers): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHousing>>): void {
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

  protected updateForm(housing: IHousing): void {
    this.editForm.patchValue({
      id: housing.id,
      housingDate: housing.housingDate,
      housingEntranceDate: housing.housingEntranceDate ? housing.housingEntranceDate.format(DATE_TIME_FORMAT) : null,
      housingExit: housing.housingExit ? housing.housingExit.format(DATE_TIME_FORMAT) : null,
      housingReceiptNumber: housing.housingReceiptNumber,
      housingDailyPrice: housing.housingDailyPrice,
      housingDescription: housing.housingDescription,
      affiliates: housing.affiliates,
      status: housing.status,
      customers: housing.customers,
      employees: housing.employees,
      parking: housing.parking,
      costCenter: housing.costCenter,
      suppliers: housing.suppliers,
      cities: housing.cities,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      housing.affiliates
    );
    this.statusesSharedCollection = this.statusService.addStatusToCollectionIfMissing(this.statusesSharedCollection, housing.status);
    this.customersSharedCollection = this.customersService.addCustomersToCollectionIfMissing(
      this.customersSharedCollection,
      housing.customers
    );
    this.employeesSharedCollection = this.employeesService.addEmployeesToCollectionIfMissing(
      this.employeesSharedCollection,
      housing.employees
    );
    this.parkingsSharedCollection = this.parkingService.addParkingToCollectionIfMissing(this.parkingsSharedCollection, housing.parking);
    this.costCentersSharedCollection = this.costCenterService.addCostCenterToCollectionIfMissing(
      this.costCentersSharedCollection,
      housing.costCenter
    );
    this.suppliersSharedCollection = this.suppliersService.addSuppliersToCollectionIfMissing(
      this.suppliersSharedCollection,
      housing.suppliers
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, housing.cities);
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

    this.statusService
      .query()
      .pipe(map((res: HttpResponse<IStatus[]>) => res.body ?? []))
      .pipe(map((statuses: IStatus[]) => this.statusService.addStatusToCollectionIfMissing(statuses, this.editForm.get('status')!.value)))
      .subscribe((statuses: IStatus[]) => (this.statusesSharedCollection = statuses));

    this.customersService
      .query()
      .pipe(map((res: HttpResponse<ICustomers[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomers[]) =>
          this.customersService.addCustomersToCollectionIfMissing(customers, this.editForm.get('customers')!.value)
        )
      )
      .subscribe((customers: ICustomers[]) => (this.customersSharedCollection = customers));

    this.employeesService
      .query()
      .pipe(map((res: HttpResponse<IEmployees[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployees[]) =>
          this.employeesService.addEmployeesToCollectionIfMissing(employees, this.editForm.get('employees')!.value)
        )
      )
      .subscribe((employees: IEmployees[]) => (this.employeesSharedCollection = employees));

    this.parkingService
      .query()
      .pipe(map((res: HttpResponse<IParking[]>) => res.body ?? []))
      .pipe(
        map((parkings: IParking[]) => this.parkingService.addParkingToCollectionIfMissing(parkings, this.editForm.get('parking')!.value))
      )
      .subscribe((parkings: IParking[]) => (this.parkingsSharedCollection = parkings));

    this.costCenterService
      .query()
      .pipe(map((res: HttpResponse<ICostCenter[]>) => res.body ?? []))
      .pipe(
        map((costCenters: ICostCenter[]) =>
          this.costCenterService.addCostCenterToCollectionIfMissing(costCenters, this.editForm.get('costCenter')!.value)
        )
      )
      .subscribe((costCenters: ICostCenter[]) => (this.costCentersSharedCollection = costCenters));

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
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing(cities, this.editForm.get('cities')!.value)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));
  }

  protected createFromForm(): IHousing {
    return {
      ...new Housing(),
      id: this.editForm.get(['id'])!.value,
      housingDate: this.editForm.get(['housingDate'])!.value,
      housingEntranceDate: this.editForm.get(['housingEntranceDate'])!.value
        ? dayjs(this.editForm.get(['housingEntranceDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      housingExit: this.editForm.get(['housingExit'])!.value
        ? dayjs(this.editForm.get(['housingExit'])!.value, DATE_TIME_FORMAT)
        : undefined,
      housingReceiptNumber: this.editForm.get(['housingReceiptNumber'])!.value,
      housingDailyPrice: this.editForm.get(['housingDailyPrice'])!.value,
      housingDescription: this.editForm.get(['housingDescription'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      status: this.editForm.get(['status'])!.value,
      customers: this.editForm.get(['customers'])!.value,
      employees: this.editForm.get(['employees'])!.value,
      parking: this.editForm.get(['parking'])!.value,
      costCenter: this.editForm.get(['costCenter'])!.value,
      suppliers: this.editForm.get(['suppliers'])!.value,
      cities: this.editForm.get(['cities'])!.value,
    };
  }
}
