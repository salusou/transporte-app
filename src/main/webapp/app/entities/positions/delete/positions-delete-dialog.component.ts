import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPositions } from '../positions.model';
import { PositionsService } from '../service/positions.service';

@Component({
  templateUrl: './positions-delete-dialog.component.html',
})
export class PositionsDeleteDialogComponent {
  positions?: IPositions;

  constructor(protected positionsService: PositionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.positionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
