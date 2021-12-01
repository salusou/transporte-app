import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompaniesXUsers } from '../companies-x-users.model';
import { CompaniesXUsersService } from '../service/companies-x-users.service';

@Component({
  templateUrl: './companies-x-users-delete-dialog.component.html',
})
export class CompaniesXUsersDeleteDialogComponent {
  companiesXUsers?: ICompaniesXUsers;

  constructor(protected companiesXUsersService: CompaniesXUsersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companiesXUsersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
