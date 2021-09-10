import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomersGroups } from '../customers-groups.model';
import { CustomersGroupsService } from '../service/customers-groups.service';

@Component({
  templateUrl: './customers-groups-delete-dialog.component.html',
})
export class CustomersGroupsDeleteDialogComponent {
  customersGroups?: ICustomersGroups;

  constructor(protected customersGroupsService: CustomersGroupsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customersGroupsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
