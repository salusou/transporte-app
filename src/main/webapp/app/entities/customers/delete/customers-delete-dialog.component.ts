import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomers } from '../customers.model';
import { CustomersService } from '../service/customers.service';

@Component({
  templateUrl: './customers-delete-dialog.component.html',
})
export class CustomersDeleteDialogComponent {
  customers?: ICustomers;

  constructor(protected customersService: CustomersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
