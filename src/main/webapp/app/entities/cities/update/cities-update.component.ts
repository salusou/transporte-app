import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICities, Cities } from '../cities.model';
import { CitiesService } from '../service/cities.service';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';

@Component({
  selector: 'jhi-cities-update',
  templateUrl: './cities-update.component.html',
})
export class CitiesUpdateComponent implements OnInit {
  isSaving = false;

  stateProvincesSharedCollection: IStateProvinces[] = [];

  editForm = this.fb.group({
    id: [],
    cityName: [null, [Validators.required]],
    latitude: [],
    longitude: [],
    stateProvinces: [null, Validators.required],
  });

  constructor(
    protected citiesService: CitiesService,
    protected stateProvincesService: StateProvincesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cities }) => {
      this.updateForm(cities);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cities = this.createFromForm();
    if (cities.id !== undefined) {
      this.subscribeToSaveResponse(this.citiesService.update(cities));
    } else {
      this.subscribeToSaveResponse(this.citiesService.create(cities));
    }
  }

  trackStateProvincesById(index: number, item: IStateProvinces): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICities>>): void {
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

  protected updateForm(cities: ICities): void {
    this.editForm.patchValue({
      id: cities.id,
      cityName: cities.cityName,
      latitude: cities.latitude,
      longitude: cities.longitude,
      stateProvinces: cities.stateProvinces,
    });

    this.stateProvincesSharedCollection = this.stateProvincesService.addStateProvincesToCollectionIfMissing(
      this.stateProvincesSharedCollection,
      cities.stateProvinces
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
  }

  protected createFromForm(): ICities {
    return {
      ...new Cities(),
      id: this.editForm.get(['id'])!.value,
      cityName: this.editForm.get(['cityName'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      stateProvinces: this.editForm.get(['stateProvinces'])!.value,
    };
  }
}
