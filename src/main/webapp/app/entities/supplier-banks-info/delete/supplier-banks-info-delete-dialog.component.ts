import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupplierBanksInfo } from '../supplier-banks-info.model';
import { SupplierBanksInfoService } from '../service/supplier-banks-info.service';

@Component({
  templateUrl: './supplier-banks-info-delete-dialog.component.html',
})
export class SupplierBanksInfoDeleteDialogComponent {
  supplierBanksInfo?: ISupplierBanksInfo;

  constructor(protected supplierBanksInfoService: SupplierBanksInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supplierBanksInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
