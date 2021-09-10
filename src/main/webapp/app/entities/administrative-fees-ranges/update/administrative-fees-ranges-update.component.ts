import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAdministrativeFeesRanges, AdministrativeFeesRanges } from '../administrative-fees-ranges.model';
import { AdministrativeFeesRangesService } from '../service/administrative-fees-ranges.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

@Component({
  selector: 'jhi-administrative-fees-ranges-update',
  templateUrl: './administrative-fees-ranges-update.component.html',
})
export class AdministrativeFeesRangesUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];

  editForm = this.fb.group({
    id: [],
    minRange: [null, [Validators.required]],
    maxRange: [null, [Validators.required]],
    aliquot: [null, [Validators.required]],
    affiliates: [null, Validators.required],
  });

  constructor(
    protected administrativeFeesRangesService: AdministrativeFeesRangesService,
    protected affiliatesService: AffiliatesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrativeFeesRanges }) => {
      this.updateForm(administrativeFeesRanges);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administrativeFeesRanges = this.createFromForm();
    if (administrativeFeesRanges.id !== undefined) {
      this.subscribeToSaveResponse(this.administrativeFeesRangesService.update(administrativeFeesRanges));
    } else {
      this.subscribeToSaveResponse(this.administrativeFeesRangesService.create(administrativeFeesRanges));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrativeFeesRanges>>): void {
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

  protected updateForm(administrativeFeesRanges: IAdministrativeFeesRanges): void {
    this.editForm.patchValue({
      id: administrativeFeesRanges.id,
      minRange: administrativeFeesRanges.minRange,
      maxRange: administrativeFeesRanges.maxRange,
      aliquot: administrativeFeesRanges.aliquot,
      affiliates: administrativeFeesRanges.affiliates,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      administrativeFeesRanges.affiliates
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

  protected createFromForm(): IAdministrativeFeesRanges {
    return {
      ...new AdministrativeFeesRanges(),
      id: this.editForm.get(['id'])!.value,
      minRange: this.editForm.get(['minRange'])!.value,
      maxRange: this.editForm.get(['maxRange'])!.value,
      aliquot: this.editForm.get(['aliquot'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
    };
  }
}
