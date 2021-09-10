import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupplierContacts } from '../supplier-contacts.model';
import { SupplierContactsService } from '../service/supplier-contacts.service';

@Component({
  templateUrl: './supplier-contacts-delete-dialog.component.html',
})
export class SupplierContactsDeleteDialogComponent {
  supplierContacts?: ISupplierContacts;

  constructor(protected supplierContactsService: SupplierContactsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supplierContactsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
