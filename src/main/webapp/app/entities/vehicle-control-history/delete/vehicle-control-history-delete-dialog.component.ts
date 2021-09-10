import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleControlHistory } from '../vehicle-control-history.model';
import { VehicleControlHistoryService } from '../service/vehicle-control-history.service';

@Component({
  templateUrl: './vehicle-control-history-delete-dialog.component.html',
})
export class VehicleControlHistoryDeleteDialogComponent {
  vehicleControlHistory?: IVehicleControlHistory;

  constructor(protected vehicleControlHistoryService: VehicleControlHistoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleControlHistoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
