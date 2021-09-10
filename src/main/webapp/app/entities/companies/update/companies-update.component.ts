import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompanies, Companies } from '../companies.model';
import { CompaniesService } from '../service/companies.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';

@Component({
  selector: 'jhi-companies-update',
  templateUrl: './companies-update.component.html',
})
export class CompaniesUpdateComponent implements OnInit {
  isSaving = false;

  citiesSharedCollection: ICities[] = [];
  stateProvincesSharedCollection: IStateProvinces[] = [];

  editForm = this.fb.group({
    id: [],
    companyName: [null, [Validators.required]],
    tradeName: [],
    companyNumber: [null, [Validators.required]],
    postalCode: [null, [Validators.maxLength(9)]],
    companyAddress: [],
    companyAddressComplement: [],
    companyAddressNumber: [],
    companyAddressNeighborhood: [],
    companyTelephone: [],
    companyEmail: [],
    responsibleContact: [],
    cities: [null, Validators.required],
    stateProvinces: [null, Validators.required],
  });

  constructor(
    protected companiesService: CompaniesService,
    protected citiesService: CitiesService,
    protected stateProvincesService: StateProvincesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companies }) => {
      this.updateForm(companies);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companies = this.createFromForm();
    if (companies.id !== undefined) {
      this.subscribeToSaveResponse(this.companiesService.update(companies));
    } else {
      this.subscribeToSaveResponse(this.companiesService.create(companies));
    }
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackStateProvincesById(index: number, item: IStateProvinces): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanies>>): void {
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

  protected updateForm(companies: ICompanies): void {
    this.editForm.patchValue({
      id: companies.id,
      companyName: companies.companyName,
      tradeName: companies.tradeName,
      companyNumber: companies.companyNumber,
      postalCode: companies.postalCode,
      companyAddress: companies.companyAddress,
      companyAddressComplement: companies.companyAddressComplement,
      companyAddressNumber: companies.companyAddressNumber,
      companyAddressNeighborhood: companies.companyAddressNeighborhood,
      companyTelephone: companies.companyTelephone,
      companyEmail: companies.companyEmail,
      responsibleContact: companies.responsibleContact,
      cities: companies.cities,
      stateProvinces: companies.stateProvinces,
    });

    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, companies.cities);
    this.stateProvincesSharedCollection = this.stateProvincesService.addStateProvincesToCollectionIfMissing(
      this.stateProvincesSharedCollection,
      companies.stateProvinces
    );
  }

  protected loadRelationshipsOptions(): void {
    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing(cities, this.editForm.get('cities')!.value)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));

    this.stateProvincesService
      .query()
      .pipe(map((res: HttpResponse<IStateProvinces[]>) => res.body ?? []))
      .pipe(
        map((stateProvinces: IStateProvinces[]) =>
          this.stateProvincesService.addStateProvincesToCollectionIfMissing(stateProvinces, this.editForm.get('stateProvinces')!.value)
        )
      )
      .subscribe((stateProvinces: IStateProvinces[]) => (this.stateProvincesSharedCollection = stateProvinces));
  }

  protected createFromForm(): ICompanies {
    return {
      ...new Companies(),
      id: this.editForm.get(['id'])!.value,
      companyName: this.editForm.get(['companyName'])!.value,
      tradeName: this.editForm.get(['tradeName'])!.value,
      companyNumber: this.editForm.get(['companyNumber'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      companyAddress: this.editForm.get(['companyAddress'])!.value,
      companyAddressComplement: this.editForm.get(['companyAddressComplement'])!.value,
      companyAddressNumber: this.editForm.get(['companyAddressNumber'])!.value,
      companyAddressNeighborhood: this.editForm.get(['companyAddressNeighborhood'])!.value,
      companyTelephone: this.editForm.get(['companyTelephone'])!.value,
      companyEmail: this.editForm.get(['companyEmail'])!.value,
      responsibleContact: this.editForm.get(['responsibleContact'])!.value,
      cities: this.editForm.get(['cities'])!.value,
      stateProvinces: this.editForm.get(['stateProvinces'])!.value,
    };
  }
}
