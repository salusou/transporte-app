import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleLocationStatus } from '../vehicle-location-status.model';
import { VehicleLocationStatusService } from '../service/vehicle-location-status.service';

@Component({
  templateUrl: './vehicle-location-status-delete-dialog.component.html',
})
export class VehicleLocationStatusDeleteDialogComponent {
  vehicleLocationStatus?: IVehicleLocationStatus;

  constructor(protected vehicleLocationStatusService: VehicleLocationStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleLocationStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
