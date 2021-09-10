import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomersContacts } from '../customers-contacts.model';
import { CustomersContactsService } from '../service/customers-contacts.service';

@Component({
  templateUrl: './customers-contacts-delete-dialog.component.html',
})
export class CustomersContactsDeleteDialogComponent {
  customersContacts?: ICustomersContacts;

  constructor(protected customersContactsService: CustomersContactsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customersContactsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
