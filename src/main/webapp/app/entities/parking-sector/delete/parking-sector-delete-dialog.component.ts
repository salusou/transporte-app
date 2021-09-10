import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParkingSector } from '../parking-sector.model';
import { ParkingSectorService } from '../service/parking-sector.service';

@Component({
  templateUrl: './parking-sector-delete-dialog.component.html',
})
export class ParkingSectorDeleteDialogComponent {
  parkingSector?: IParkingSector;

  constructor(protected parkingSectorService: ParkingSectorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parkingSectorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
