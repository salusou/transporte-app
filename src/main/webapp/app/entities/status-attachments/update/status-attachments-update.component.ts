import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStatusAttachments, StatusAttachments } from '../status-attachments.model';
import { StatusAttachmentsService } from '../service/status-attachments.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

@Component({
  selector: 'jhi-status-attachments-update',
  templateUrl: './status-attachments-update.component.html',
})
export class StatusAttachmentsUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];

  editForm = this.fb.group({
    id: [],
    statusName: [null, [Validators.required]],
    statusDescriptions: [],
    statusObs: [],
    attachmentType: [],
    affiliates: [null, Validators.required],
  });

  constructor(
    protected statusAttachmentsService: StatusAttachmentsService,
    protected affiliatesService: AffiliatesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusAttachments }) => {
      this.updateForm(statusAttachments);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusAttachments = this.createFromForm();
    if (statusAttachments.id !== undefined) {
      this.subscribeToSaveResponse(this.statusAttachmentsService.update(statusAttachments));
    } else {
      this.subscribeToSaveResponse(this.statusAttachmentsService.create(statusAttachments));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusAttachments>>): void {
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

  protected updateForm(statusAttachments: IStatusAttachments): void {
    this.editForm.patchValue({
      id: statusAttachments.id,
      statusName: statusAttachments.statusName,
      statusDescriptions: statusAttachments.statusDescriptions,
      statusObs: statusAttachments.statusObs,
      attachmentType: statusAttachments.attachmentType,
      affiliates: statusAttachments.affiliates,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      statusAttachments.affiliates
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

  protected createFromForm(): IStatusAttachments {
    return {
      ...new StatusAttachments(),
      id: this.editForm.get(['id'])!.value,
      statusName: this.editForm.get(['statusName'])!.value,
      statusDescriptions: this.editForm.get(['statusDescriptions'])!.value,
      statusObs: this.editForm.get(['statusObs'])!.value,
      attachmentType: this.editForm.get(['attachmentType'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
    };
  }
}
