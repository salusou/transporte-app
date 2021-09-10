import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAffiliates, Affiliates } from '../affiliates.model';
import { AffiliatesService } from '../service/affiliates.service';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';
import { ICities } from 'app/entities/cities/cities.model';
import { CitiesService } from 'app/entities/cities/service/cities.service';
import { ICompanies } from 'app/entities/companies/companies.model';
import { CompaniesService } from 'app/entities/companies/service/companies.service';

@Component({
  selector: 'jhi-affiliates-update',
  templateUrl: './affiliates-update.component.html',
})
export class AffiliatesUpdateComponent implements OnInit {
  isSaving = false;

  stateProvincesSharedCollection: IStateProvinces[] = [];
  citiesSharedCollection: ICities[] = [];
  companiesSharedCollection: ICompanies[] = [];

  editForm = this.fb.group({
    id: [],
    branchName: [null, [Validators.required]],
    branchNumber: [null, [Validators.required]],
    useSameCompanyAddress: [],
    branchPostalCode: [null, [Validators.maxLength(9)]],
    branchAddress: [],
    branchAddressComplement: [],
    branchAddressNumber: [],
    branchAddressNeighborhood: [],
    branchTelephone: [],
    branchEmail: [],
    responsibleContact: [],
    stateProvinces: [null, Validators.required],
    cities: [null, Validators.required],
    companies: [null, Validators.required],
  });

  constructor(
    protected affiliatesService: AffiliatesService,
    protected stateProvincesService: StateProvincesService,
    protected citiesService: CitiesService,
    protected companiesService: CompaniesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affiliates }) => {
      this.updateForm(affiliates);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const affiliates = this.createFromForm();
    if (affiliates.id !== undefined) {
      this.subscribeToSaveResponse(this.affiliatesService.update(affiliates));
    } else {
      this.subscribeToSaveResponse(this.affiliatesService.create(affiliates));
    }
  }

  trackStateProvincesById(index: number, item: IStateProvinces): number {
    return item.id!;
  }

  trackCitiesById(index: number, item: ICities): number {
    return item.id!;
  }

  trackCompaniesById(index: number, item: ICompanies): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliates>>): void {
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

  protected updateForm(affiliates: IAffiliates): void {
    this.editForm.patchValue({
      id: affiliates.id,
      branchName: affiliates.branchName,
      branchNumber: affiliates.branchNumber,
      useSameCompanyAddress: affiliates.useSameCompanyAddress,
      branchPostalCode: affiliates.branchPostalCode,
      branchAddress: affiliates.branchAddress,
      branchAddressComplement: affiliates.branchAddressComplement,
      branchAddressNumber: affiliates.branchAddressNumber,
      branchAddressNeighborhood: affiliates.branchAddressNeighborhood,
      branchTelephone: affiliates.branchTelephone,
      branchEmail: affiliates.branchEmail,
      responsibleContact: affiliates.responsibleContact,
      stateProvinces: affiliates.stateProvinces,
      cities: affiliates.cities,
      companies: affiliates.companies,
    });

    this.stateProvincesSharedCollection = this.stateProvincesService.addStateProvincesToCollectionIfMissing(
      this.stateProvincesSharedCollection,
      affiliates.stateProvinces
    );
    this.citiesSharedCollection = this.citiesService.addCitiesToCollectionIfMissing(this.citiesSharedCollection, affiliates.cities);
    this.companiesSharedCollection = this.companiesService.addCompaniesToCollectionIfMissing(
      this.companiesSharedCollection,
      affiliates.companies
    );
  }

  protected loadRelationshipsOptions(): void {
    this.stateProvincesService
      .query()
      .pipe(map((res: HttpResponse<IStateProvinces[]>) => res.body ?? []))
      .pipe(
        map((stateProvinces: IStateProvinces[]) =>
          this.stateProvincesService.addStateProvincesToCollectionIfMissing(stateProvinces, this.editForm.get('stateProvinces')!.value)
        )
      )
      .subscribe((stateProvinces: IStateProvinces[]) => (this.stateProvincesSharedCollection = stateProvinces));

    this.citiesService
      .query()
      .pipe(map((res: HttpResponse<ICities[]>) => res.body ?? []))
      .pipe(map((cities: ICities[]) => this.citiesService.addCitiesToCollectionIfMissing(cities, this.editForm.get('cities')!.value)))
      .subscribe((cities: ICities[]) => (this.citiesSharedCollection = cities));

    this.companiesService
      .query()
      .pipe(map((res: HttpResponse<ICompanies[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompanies[]) =>
          this.companiesService.addCompaniesToCollectionIfMissing(companies, this.editForm.get('companies')!.value)
        )
      )
      .subscribe((companies: ICompanies[]) => (this.companiesSharedCollection = companies));
  }

  protected createFromForm(): IAffiliates {
    return {
      ...new Affiliates(),
      id: this.editForm.get(['id'])!.value,
      branchName: this.editForm.get(['branchName'])!.value,
      branchNumber: this.editForm.get(['branchNumber'])!.value,
      useSameCompanyAddress: this.editForm.get(['useSameCompanyAddress'])!.value,
      branchPostalCode: this.editForm.get(['branchPostalCode'])!.value,
      branchAddress: this.editForm.get(['branchAddress'])!.value,
      branchAddressComplement: this.editForm.get(['branchAddressComplement'])!.value,
      branchAddressNumber: this.editForm.get(['branchAddressNumber'])!.value,
      branchAddressNeighborhood: this.editForm.get(['branchAddressNeighborhood'])!.value,
      branchTelephone: this.editForm.get(['branchTelephone'])!.value,
      branchEmail: this.editForm.get(['branchEmail'])!.value,
      responsibleContact: this.editForm.get(['responsibleContact'])!.value,
      stateProvinces: this.editForm.get(['stateProvinces'])!.value,
      cities: this.editForm.get(['cities'])!.value,
      companies: this.editForm.get(['companies'])!.value,
    };
  }
}
