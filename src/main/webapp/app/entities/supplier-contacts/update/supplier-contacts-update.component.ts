import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISupplierContacts, SupplierContacts } from '../supplier-contacts.model';
import { SupplierContactsService } from '../service/supplier-contacts.service';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';

@Component({
  selector: 'jhi-supplier-contacts-update',
  templateUrl: './supplier-contacts-update.component.html',
})
export class SupplierContactsUpdateComponent implements OnInit {
  isSaving = false;

  suppliersSharedCollection: ISuppliers[] = [];

  editForm = this.fb.group({
    id: [],
    supplierContactName: [null, [Validators.required]],
    supplierContactPhone: [],
    supplierContactEmail: [],
    suppliers: [null, Validators.required],
  });

  constructor(
    protected supplierContactsService: SupplierContactsService,
    protected suppliersService: SuppliersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierContacts }) => {
      this.updateForm(supplierContacts);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supplierContacts = this.createFromForm();
    if (supplierContacts.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierContactsService.update(supplierContacts));
    } else {
      this.subscribeToSaveResponse(this.supplierContactsService.create(supplierContacts));
    }
  }

  trackSuppliersById(index: number, item: ISuppliers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierContacts>>): void {
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

  protected updateForm(supplierContacts: ISupplierContacts): void {
    this.editForm.patchValue({
      id: supplierContacts.id,
      supplierContactName: supplierContacts.supplierContactName,
      supplierContactPhone: supplierContacts.supplierContactPhone,
      supplierContactEmail: supplierContacts.supplierContactEmail,
      suppliers: supplierContacts.suppliers,
    });

    this.suppliersSharedCollection = this.suppliersService.addSuppliersToCollectionIfMissing(
      this.suppliersSharedCollection,
      supplierContacts.suppliers
    );
  }

  protected loadRelationshipsOptions(): void {
    this.suppliersService
      .query()
      .pipe(map((res: HttpResponse<ISuppliers[]>) => res.body ?? []))
      .pipe(
        map((suppliers: ISuppliers[]) =>
          this.suppliersService.addSuppliersToCollectionIfMissing(suppliers, this.editForm.get('suppliers')!.value)
        )
      )
      .subscribe((suppliers: ISuppliers[]) => (this.suppliersSharedCollection = suppliers));
  }

  protected createFromForm(): ISupplierContacts {
    return {
      ...new SupplierContacts(),
      id: this.editForm.get(['id'])!.value,
      supplierContactName: this.editForm.get(['supplierContactName'])!.value,
      supplierContactPhone: this.editForm.get(['supplierContactPhone'])!.value,
      supplierContactEmail: this.editForm.get(['supplierContactEmail'])!.value,
      suppliers: this.editForm.get(['suppliers'])!.value,
    };
  }
}
