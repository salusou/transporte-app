import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPositions, Positions } from '../positions.model';
import { PositionsService } from '../service/positions.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

@Component({
  selector: 'jhi-positions-update',
  templateUrl: './positions-update.component.html',
})
export class PositionsUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];

  editForm = this.fb.group({
    id: [],
    positionName: [null, [Validators.required]],
    affiliates: [null, Validators.required],
  });

  constructor(
    protected positionsService: PositionsService,
    protected affiliatesService: AffiliatesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ positions }) => {
      this.updateForm(positions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const positions = this.createFromForm();
    if (positions.id !== undefined) {
      this.subscribeToSaveResponse(this.positionsService.update(positions));
    } else {
      this.subscribeToSaveResponse(this.positionsService.create(positions));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPositions>>): void {
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

  protected updateForm(positions: IPositions): void {
    this.editForm.patchValue({
      id: positions.id,
      positionName: positions.positionName,
      affiliates: positions.affiliates,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      positions.affiliates
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

  protected createFromForm(): IPositions {
    return {
      ...new Positions(),
      id: this.editForm.get(['id'])!.value,
      positionName: this.editForm.get(['positionName'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
    };
  }
}
