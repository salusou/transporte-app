import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInsurances } from '../insurances.model';
import { InsurancesService } from '../service/insurances.service';

@Component({
  templateUrl: './insurances-delete-dialog.component.html',
})
export class InsurancesDeleteDialogComponent {
  insurances?: IInsurances;

  constructor(protected insurancesService: InsurancesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.insurancesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
