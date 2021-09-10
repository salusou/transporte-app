import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISupplierBanksInfo, SupplierBanksInfo } from '../supplier-banks-info.model';
import { SupplierBanksInfoService } from '../service/supplier-banks-info.service';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/service/suppliers.service';

@Component({
  selector: 'jhi-supplier-banks-info-update',
  templateUrl: './supplier-banks-info-update.component.html',
})
export class SupplierBanksInfoUpdateComponent implements OnInit {
  isSaving = false;

  suppliersSharedCollection: ISuppliers[] = [];

  editForm = this.fb.group({
    id: [],
    supplierBankCode: [null, [Validators.required]],
    supplierBankName: [null, [Validators.required]],
    supplierBankBranchCode: [],
    supplierBankAccountNumber: [],
    supplierBankUserName: [],
    supplierBankPixKey: [],
    supplierBankUserNumber: [],
    suppliers: [null, Validators.required],
  });

  constructor(
    protected supplierBanksInfoService: SupplierBanksInfoService,
    protected suppliersService: SuppliersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierBanksInfo }) => {
      this.updateForm(supplierBanksInfo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supplierBanksInfo = this.createFromForm();
    if (supplierBanksInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierBanksInfoService.update(supplierBanksInfo));
    } else {
      this.subscribeToSaveResponse(this.supplierBanksInfoService.create(supplierBanksInfo));
    }
  }

  trackSuppliersById(index: number, item: ISuppliers): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierBanksInfo>>): void {
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

  protected updateForm(supplierBanksInfo: ISupplierBanksInfo): void {
    this.editForm.patchValue({
      id: supplierBanksInfo.id,
      supplierBankCode: supplierBanksInfo.supplierBankCode,
      supplierBankName: supplierBanksInfo.supplierBankName,
      supplierBankBranchCode: supplierBanksInfo.supplierBankBranchCode,
      supplierBankAccountNumber: supplierBanksInfo.supplierBankAccountNumber,
      supplierBankUserName: supplierBanksInfo.supplierBankUserName,
      supplierBankPixKey: supplierBanksInfo.supplierBankPixKey,
      supplierBankUserNumber: supplierBanksInfo.supplierBankUserNumber,
      suppliers: supplierBanksInfo.suppliers,
    });

    this.suppliersSharedCollection = this.suppliersService.addSuppliersToCollectionIfMissing(
      this.suppliersSharedCollection,
      supplierBanksInfo.suppliers
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

  protected createFromForm(): ISupplierBanksInfo {
    return {
      ...new SupplierBanksInfo(),
      id: this.editForm.get(['id'])!.value,
      supplierBankCode: this.editForm.get(['supplierBankCode'])!.value,
      supplierBankName: this.editForm.get(['supplierBankName'])!.value,
      supplierBankBranchCode: this.editForm.get(['supplierBankBranchCode'])!.value,
      supplierBankAccountNumber: this.editForm.get(['supplierBankAccountNumber'])!.value,
      supplierBankUserName: this.editForm.get(['supplierBankUserName'])!.value,
      supplierBankPixKey: this.editForm.get(['supplierBankPixKey'])!.value,
      supplierBankUserNumber: this.editForm.get(['supplierBankUserNumber'])!.value,
      suppliers: this.editForm.get(['suppliers'])!.value,
    };
  }
}
