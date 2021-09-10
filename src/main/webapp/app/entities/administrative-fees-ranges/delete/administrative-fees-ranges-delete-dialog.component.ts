import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministrativeFeesRanges } from '../administrative-fees-ranges.model';
import { AdministrativeFeesRangesService } from '../service/administrative-fees-ranges.service';

@Component({
  templateUrl: './administrative-fees-ranges-delete-dialog.component.html',
})
export class AdministrativeFeesRangesDeleteDialogComponent {
  administrativeFeesRanges?: IAdministrativeFeesRanges;

  constructor(protected administrativeFeesRangesService: AdministrativeFeesRangesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.administrativeFeesRangesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
