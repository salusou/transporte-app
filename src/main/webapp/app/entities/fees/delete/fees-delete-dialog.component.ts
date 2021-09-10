import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFees } from '../fees.model';
import { FeesService } from '../service/fees.service';

@Component({
  templateUrl: './fees-delete-dialog.component.html',
})
export class FeesDeleteDialogComponent {
  fees?: IFees;

  constructor(protected feesService: FeesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
