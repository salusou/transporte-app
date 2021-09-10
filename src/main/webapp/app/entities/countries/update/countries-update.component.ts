import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICountries, Countries } from '../countries.model';
import { CountriesService } from '../service/countries.service';

@Component({
  selector: 'jhi-countries-update',
  templateUrl: './countries-update.component.html',
})
export class CountriesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    countryName: [null, [Validators.required]],
    iso2: [null, [Validators.required]],
    codeArea: [],
    language: [null, [Validators.maxLength(6)]],
  });

  constructor(protected countriesService: CountriesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ countries }) => {
      this.updateForm(countries);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const countries = this.createFromForm();
    if (countries.id !== undefined) {
      this.subscribeToSaveResponse(this.countriesService.update(countries));
    } else {
      this.subscribeToSaveResponse(this.countriesService.create(countries));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountries>>): void {
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

  protected updateForm(countries: ICountries): void {
    this.editForm.patchValue({
      id: countries.id,
      countryName: countries.countryName,
      iso2: countries.iso2,
      codeArea: countries.codeArea,
      language: countries.language,
    });
  }

  protected createFromForm(): ICountries {
    return {
      ...new Countries(),
      id: this.editForm.get(['id'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
      iso2: this.editForm.get(['iso2'])!.value,
      codeArea: this.editForm.get(['codeArea'])!.value,
      language: this.editForm.get(['language'])!.value,
    };
  }
}
