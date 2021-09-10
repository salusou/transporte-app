import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStateProvinces, StateProvinces } from '../state-provinces.model';
import { StateProvincesService } from '../service/state-provinces.service';
import { ICountries } from 'app/entities/countries/countries.model';
import { CountriesService } from 'app/entities/countries/service/countries.service';

@Component({
  selector: 'jhi-state-provinces-update',
  templateUrl: './state-provinces-update.component.html',
})
export class StateProvincesUpdateComponent implements OnInit {
  isSaving = false;

  countriesSharedCollection: ICountries[] = [];

  editForm = this.fb.group({
    id: [],
    stateName: [null, [Validators.required]],
    abbreviation: [null, [Validators.required]],
    countries: [null, Validators.required],
  });

  constructor(
    protected stateProvincesService: StateProvincesService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stateProvinces }) => {
      this.updateForm(stateProvinces);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stateProvinces = this.createFromForm();
    if (stateProvinces.id !== undefined) {
      this.subscribeToSaveResponse(this.stateProvincesService.update(stateProvinces));
    } else {
      this.subscribeToSaveResponse(this.stateProvincesService.create(stateProvinces));
    }
  }

  trackCountriesById(index: number, item: ICountries): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateProvinces>>): void {
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

  protected updateForm(stateProvinces: IStateProvinces): void {
    this.editForm.patchValue({
      id: stateProvinces.id,
      stateName: stateProvinces.stateName,
      abbreviation: stateProvinces.abbreviation,
      countries: stateProvinces.countries,
    });

    this.countriesSharedCollection = this.countriesService.addCountriesToCollectionIfMissing(
      this.countriesSharedCollection,
      stateProvinces.countries
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countriesService
      .query()
      .pipe(map((res: HttpResponse<ICountries[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountries[]) =>
          this.countriesService.addCountriesToCollectionIfMissing(countries, this.editForm.get('countries')!.value)
        )
      )
      .subscribe((countries: ICountries[]) => (this.countriesSharedCollection = countries));
  }

  protected createFromForm(): IStateProvinces {
    return {
      ...new StateProvinces(),
      id: this.editForm.get(['id'])!.value,
      stateName: this.editForm.get(['stateName'])!.value,
      abbreviation: this.editForm.get(['abbreviation'])!.value,
      countries: this.editForm.get(['countries'])!.value,
    };
  }
}
