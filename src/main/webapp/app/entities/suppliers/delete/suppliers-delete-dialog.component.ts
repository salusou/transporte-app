import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISuppliers } from '../suppliers.model';
import { SuppliersService } from '../service/suppliers.service';

@Component({
  templateUrl: './suppliers-delete-dialog.component.html',
})
export class SuppliersDeleteDialogComponent {
  suppliers?: ISuppliers;

  constructor(protected suppliersService: SuppliersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.suppliersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
