import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICustomers, Customers } from '../customers.model';
import { CustomersService } from '../service/customers.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';
import { CustomersGroupsService } from 'app/entities/customers-groups/service/customers-groups.service';

@Component({
  selector: 'jhi-customers-update',
  templateUrl: './customers-update.component.html',
})
export class CustomersUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];
  citiesSharedCollection: ICities[] = [];
  customersGroupsSharedCollection: ICustomersGroups[] = [];

  editForm = this.fb.group({
    id: [],
    customerName: [null, [Validators.required]],
    active: [null, [Validators.required]],
    customerNumber: [null, [Validators.required]],
    customerPostalCode: [null, [Validators.maxLength(9)]],
    customerAddress: [],
    customerAddressComplement: [],
    customerAddressNumber: [],
    customerAddressNeighborhood: [],
    customerTelephone: [],
    paymentTerm: [null, [Validators.required]],
    affiliates: [null, Validators.required],
    cities: [null, Validators.required],
    customersGroups: [],
  });

  constructor(
    protected customersService: CustomersService,
    protected affiliatesService: AffiliatesService,
    protected citiesService: CitiesService,
    protected customersGroupsService: CustomersGroupsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customers }) => {
      this.updateForm(customers);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customers = this.createFromForm();
    if (customers.id !== undefined) {
      this.subscribeToSaveResponse(this.customersService.update(customers));
    } else {
      this.subscribeToSaveResponse(this.customersService.create(customers));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackCustomersGroupsById(index: number, item: ICustomersGroups): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomers>>): void {
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

  protected updateForm(customers: ICustomers): void {
    this.editForm.patchValue({
      id: customers.id,
      customerName: customers.customerName,
      active: customers.active,
      customerNumber: customers.customerNumber,
      customerPostalCode: customers.customerPostalCode,
      customerAddress: customers.customerAddress,
      customerAddressComplement: customers.customerAddressComplement,
      customerAddressNumber: customers.customerAddressNumber,
      customerAddressNeighborhood: customers.customerAddressNeighborhood,
      customerTelephone: customers.customerTelephone,
      paymentTerm: customers.paymentTerm,
      affiliates: customers.affiliates,
      cities: customers.cities,
      customersGroups: customers.customersGroups,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      customers.affiliates
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, customers.cities);
    this.customersGroupsSharedCollection = this.customersGroupsService.addCustomersGroupsToCollectionIfMissing(
      this.customersGroupsSharedCollection,
      customers.customersGroups
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

    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing(cities, this.editForm.get('cities')!.value)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));

    this.customersGroupsService
      .query()
      .pipe(map((res: HttpResponse<ICustomersGroups[]>) => res.body ?? []))
      .pipe(
        map((customersGroups: ICustomersGroups[]) =>
          this.customersGroupsService.addCustomersGroupsToCollectionIfMissing(customersGroups, this.editForm.get('customersGroups')!.value)
        )
      )
      .subscribe((customersGroups: ICustomersGroups[]) => (this.customersGroupsSharedCollection = customersGroups));
  }

  protected createFromForm(): ICustomers {
    return {
      ...new Customers(),
      id: this.editForm.get(['id'])!.value,
      customerName: this.editForm.get(['customerName'])!.value,
      active: this.editForm.get(['active'])!.value,
      customerNumber: this.editForm.get(['customerNumber'])!.value,
      customerPostalCode: this.editForm.get(['customerPostalCode'])!.value,
      customerAddress: this.editForm.get(['customerAddress'])!.value,
      customerAddressComplement: this.editForm.get(['customerAddressComplement'])!.value,
      customerAddressNumber: this.editForm.get(['customerAddressNumber'])!.value,
      customerAddressNeighborhood: this.editForm.get(['customerAddressNeighborhood'])!.value,
      customerTelephone: this.editForm.get(['customerTelephone'])!.value,
      paymentTerm: this.editForm.get(['paymentTerm'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      cities: this.editForm.get(['cities'])!.value,
      customersGroups: this.editForm.get(['customersGroups'])!.value,
    };
  }
}
