import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFees, Fees } from '../fees.model';
import { FeesService } from '../service/fees.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

@Component({
  selector: 'jhi-fees-update',
  templateUrl: './fees-update.component.html',
})
export class FeesUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];

  editForm = this.fb.group({
    id: [],
    feeDate: [null, [Validators.required]],
    feeDriverCommission: [null, [Validators.required]],
    feeFinancialCost: [null, [Validators.required]],
    feeTaxes: [null, [Validators.required]],
    feeDescriptions: [],
    affiliates: [null, Validators.required],
  });

  constructor(
    protected feesService: FeesService,
    protected affiliatesService: AffiliatesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fees }) => {
      this.updateForm(fees);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fees = this.createFromForm();
    if (fees.id !== undefined) {
      this.subscribeToSaveResponse(this.feesService.update(fees));
    } else {
      this.subscribeToSaveResponse(this.feesService.create(fees));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFees>>): void {
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

  protected updateForm(fees: IFees): void {
    this.editForm.patchValue({
      id: fees.id,
      feeDate: fees.feeDate,
      feeDriverCommission: fees.feeDriverCommission,
      feeFinancialCost: fees.feeFinancialCost,
      feeTaxes: fees.feeTaxes,
      feeDescriptions: fees.feeDescriptions,
      affiliates: fees.affiliates,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      fees.affiliates
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

  protected createFromForm(): IFees {
    return {
      ...new Fees(),
      id: this.editForm.get(['id'])!.value,
      feeDate: this.editForm.get(['feeDate'])!.value,
      feeDriverCommission: this.editForm.get(['feeDriverCommission'])!.value,
      feeFinancialCost: this.editForm.get(['feeFinancialCost'])!.value,
      feeTaxes: this.editForm.get(['feeTaxes'])!.value,
      feeDescriptions: this.editForm.get(['feeDescriptions'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
    };
  }
}
