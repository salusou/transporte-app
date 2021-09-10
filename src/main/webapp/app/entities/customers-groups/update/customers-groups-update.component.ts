import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICustomersGroups, CustomersGroups } from '../customers-groups.model';
import { CustomersGroupsService } from '../service/customers-groups.service';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AffiliatesService } from 'app/entities/affiliates/service/affiliates.service';

@Component({
  selector: 'jhi-customers-groups-update',
  templateUrl: './customers-groups-update.component.html',
})
export class CustomersGroupsUpdateComponent implements OnInit {
  isSaving = false;

  affiliatesSharedCollection: IAffiliates[] = [];

  editForm = this.fb.group({
    id: [],
    groupName: [null, [Validators.required]],
    affiliates: [null, Validators.required],
  });

  constructor(
    protected customersGroupsService: CustomersGroupsService,
    protected affiliatesService: AffiliatesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customersGroups }) => {
      this.updateForm(customersGroups);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customersGroups = this.createFromForm();
    if (customersGroups.id !== undefined) {
      this.subscribeToSaveResponse(this.customersGroupsService.update(customersGroups));
    } else {
      this.subscribeToSaveResponse(this.customersGroupsService.create(customersGroups));
    }
  }

  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomersGroups>>): void {
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

  protected updateForm(customersGroups: ICustomersGroups): void {
    this.editForm.patchValue({
      id: customersGroups.id,
      groupName: customersGroups.groupName,
      affiliates: customersGroups.affiliates,
    });

    this.affiliatesSharedCollection = this.affiliatesService.addAffiliatesToCollectionIfMissing(
      this.affiliatesSharedCollection,
      customersGroups.affiliates
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

  protected createFromForm(): ICustomersGroups {
    return {
      ...new CustomersGroups(),
      id: this.editForm.get(['id'])!.value,
      groupName: this.editForm.get(['groupName'])!.value,
      affiliates: this.editForm.get(['affiliates'])!.value,
    };
  }
}
