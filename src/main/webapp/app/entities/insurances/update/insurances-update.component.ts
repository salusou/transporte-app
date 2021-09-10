import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IInsurances, Insurances } from '../insurances.model';
import { InsurancesService } from '../service/insurances.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/service/state-provinces.service';

@Component({
  selector: 'jhi-insurances-update',
  templateUrl: './insurances-update.component.html',
})
export class InsurancesUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];
  stateProvincesSharedCollection: IStateProvinces[] = [];

  editForm = this.fb.group({
    id: [],
    insurancesPercent: [null, [Validators.required]],
    affiliates: [null, Validators.required],
    toStateProvince: [null, Validators.required],
    forStateProvince: [null, Validators.required],
  });

  constructor(
    protected insurancesService: InsurancesService,
    protected affiliatesService: AffiliatesService,
    protected stateProvincesService: StateProvincesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insurances }) => {
      this.updateForm(insurances);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const insurances = this.createFromForm();
    if (insurances.id !== undefined) {
      this.subscribeToSaveResponse(this.insurancesService.update(insurances));
    } else {
      this.subscribeToSaveResponse(this.insurancesService.create(insurances));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  trackStateProvincesById(index: number, item: IStateProvinces): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsurances>>): void {
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

  protected updateForm(insurances: IInsurances): void {
    this.editForm.patchValue({
      id: insurances.id,
      insurancesPercent: insurances.insurancesPercent,
      affiliates: insurances.affiliates,
      toStateProvince: insurances.toStateProvince,
      forStateProvince: insurances.forStateProvince,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      insurances.affiliates
    );
    this.stateProvincesSharedCollection = this.stateProvincesService.addStateProvincesToCollectionIfMissing(
      this.stateProvincesSharedCollection,
      insurances.toStateProvince,
      insurances.forStateProvince
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

    this.stateProvincesService
      .query()
      .pipe(map((res: HttpResponse<IStateProvinces[]>) => res.body ?? []))
      .pipe(
        map((stateProvinces: IStateProvinces[]) =>
          this.stateProvincesService.addStateProvincesToCollectionIfMissing(
            stateProvinces,
            this.editForm.get('toStateProvince')!.value,
            this.editForm.get('forStateProvince')!.value
          )
        )
      )
      .subscribe((stateProvinces: IStateProvinces[]) => (this.stateProvincesSharedCollection = stateProvinces));
  }

  protected createFromForm(): IInsurances {
    return {
      ...new Insurances(),
      id: this.editForm.get(['id'])!.value,
      insurancesPercent: this.editForm.get(['insurancesPercent'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
      toStateProvince: this.editForm.get(['toStateProvince'])!.value,
      forStateProvince: this.editForm.get(['forStateProvince'])!.value,
    };
  }
}
