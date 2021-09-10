import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParkingSectorSpace } from '../parking-sector-space.model';
import { ParkingSectorSpaceService } from '../service/parking-sector-space.service';

@Component({
  templateUrl: './parking-sector-space-delete-dialog.component.html',
})
export class ParkingSectorSpaceDeleteDialogComponent {
  parkingSectorSpace?: IParkingSectorSpace;

  constructor(protected parkingSectorSpaceService: ParkingSectorSpaceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parkingSectorSpaceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
