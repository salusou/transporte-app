import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAffiliates } from '../affiliates.model';
import { AffiliatesService } from '../service/affiliates.service';

@Component({
  templateUrl: './affiliates-delete-dialog.component.html',
})
export class AffiliatesDeleteDialogComponent {
  affiliates?: IAffiliates;

  constructor(protected affiliatesService: AffiliatesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.affiliatesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
