import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICostCenter, CostCenter } from '../cost-center.model';
import { CostCenterService } from '../service/cost-center.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

@Component({
  selector: 'jhi-cost-center-update',
  templateUrl: './cost-center-update.component.html',
})
export class CostCenterUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];

  editForm = this.fb.group({
    id: [],
    costCenterName: [null, [Validators.required]],
    affiliates: [null, Validators.required],
  });

  constructor(
    protected costCenterService: CostCenterService,
    protected affiliatesService: AffiliatesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ costCenter }) => {
      this.updateForm(costCenter);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const costCenter = this.createFromForm();
    if (costCenter.id !== undefined) {
      this.subscribeToSaveResponse(this.costCenterService.update(costCenter));
    } else {
      this.subscribeToSaveResponse(this.costCenterService.create(costCenter));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICostCenter>>): void {
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

  protected updateForm(costCenter: ICostCenter): void {
    this.editForm.patchValue({
      id: costCenter.id,
      costCenterName: costCenter.costCenterName,
      affiliates: costCenter.affiliates,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      costCenter.affiliates
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
  }

  protected createFromForm(): ICostCenter {
    return {
      ...new CostCenter(),
      id: this.editForm.get(['id'])!.value,
      costCenterName: this.editForm.get(['costCenterName'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
    };
  }
}
