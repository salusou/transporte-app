import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISuppliers, Suppliers } from '../suppliers.model';
import { SuppliersService } from '../service/suppliers.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IServiceProvided } from 'app/entities/service-provided/service-provided.model';
import { ServiceProvidedService } from 'app/entities/service-provided/service/service-provided.service';

@Component({
  selector: 'jhi-suppliers-update',
  templateUrl: './suppliers-update.component.html',
})
export class SuppliersUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];
  citiesSharedCollection: ICities[] = [];
  serviceProvidedsSharedCollection: IServiceProvided[] = [];

  editForm = this.fb.group({
    id: [],
    supplierName: [null, [Validators.required]],
    supplierNumber: [],
    supplierPostalCode: [null, [Validators.maxLength(9)]],
    supplierAddress: [],
    supplierAddressComplement: [],
    supplierAddressNumber: [],
    supplierAddressNeighborhood: [],
    supplierTelephone: [],
    supplierEmail: [],
    supplierContactName: [],
    affiliates: [null, Validators.required],
    cities: [null, Validators.required],
    serviceProvided: [null, Validators.required],
  });

  constructor(
    protected suppliersService: SuppliersService,
    protected affiliatesService: AffiliatesService,
    protected citiesService: CitiesService,
    protected serviceProvidedService: ServiceProvidedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suppliers }) => {
      this.updateForm(suppliers);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const suppliers = this.createFromForm();
    if (suppliers.id !== undefined) {
      this.subscribeToSaveResponse(this.suppliersService.update(suppliers));
    } else {
      this.subscribeToSaveResponse(this.suppliersService.create(suppliers));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackServiceProvidedById(index: number, item: IServiceProvided): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuppliers>>): void {
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

  protected updateForm(suppliers: ISuppliers): void {
    this.editForm.patchValue({
      id: suppliers.id,
      supplierName: suppliers.supplierName,
      supplierNumber: suppliers.supplierNumber,
      supplierPostalCode: suppliers.supplierPostalCode,
      supplierAddress: suppliers.supplierAddress,
      supplierAddressComplement: suppliers.supplierAddressComplement,
      supplierAddressNumber: suppliers.supplierAddressNumber,
      supplierAddressNeighborhood: suppliers.supplierAddressNeighborhood,
      supplierTelephone: suppliers.supplierTelephone,
      supplierEmail: suppliers.supplierEmail,
      supplierContactName: suppliers.supplierContactName,
      affiliates: suppliers.affiliates,
      cities: suppliers.cities,
      serviceProvided: suppliers.serviceProvided,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      suppliers.affiliates
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, suppliers.cities);
    this.serviceProvidedsSharedCollection = this.serviceProvidedService.addServiceProvidedToCollectionIfMissing(
      this.serviceProvidedsSharedCollection,
      suppliers.serviceProvided
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

    this.serviceProvidedService
      .query()
      .pipe(map((res: HttpResponse<IServiceProvided[]>) => res.body ?? []))
      .pipe(
        map((serviceProvideds: IServiceProvided[]) =>
          this.serviceProvidedService.addServiceProvidedToCollectionIfMissing(serviceProvideds, this.editForm.get('serviceProvided')!.value)
        )
      )
      .subscribe((serviceProvideds: IServiceProvided[]) => (this.serviceProvidedsSharedCollection = serviceProvideds));
  }

  protected createFromForm(): ISuppliers {
    return {
      ...new Suppliers(),
      id: this.editForm.get(['id'])!.value,
      supplierName: this.editForm.get(['supplierName'])!.value,
      supplierNumber: this.editForm.get(['supplierNumber'])!.value,
      supplierPostalCode: this.editForm.get(['supplierPostalCode'])!.value,
      supplierAddress: this.editForm.get(['supplierAddress'])!.value,
      supplierAddressComplement: this.editForm.get(['supplierAddressComplement'])!.value,
      supplierAddressNumber: this.editForm.get(['supplierAddressNumber'])!.value,
      supplierAddressNeighborhood: this.editForm.get(['supplierAddressNeighborhood'])!.value,
      supplierTelephone: this.editForm.get(['supplierTelephone'])!.value,
      supplierEmail: this.editForm.get(['supplierEmail'])!.value,
      supplierContactName: this.editForm.get(['supplierContactName'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      cities: this.editForm.get(['cities'])!.value,
      serviceProvided: this.editForm.get(['serviceProvided'])!.value,
    };
  }
}
