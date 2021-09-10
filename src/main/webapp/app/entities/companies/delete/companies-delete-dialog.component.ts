import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanies } from '../companies.model';
import { CompaniesService } from '../service/companies.service';

@Component({
  templateUrl: './companies-delete-dialog.component.html',
})
export class CompaniesDeleteDialogComponent {
  companies?: ICompanies;

  constructor(protected companiesService: CompaniesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companiesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
