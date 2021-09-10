import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleControls } from '../vehicle-controls.model';
import { VehicleControlsService } from '../service/vehicle-controls.service';

@Component({
  templateUrl: './vehicle-controls-delete-dialog.component.html',
})
export class VehicleControlsDeleteDialogComponent {
  vehicleControls?: IVehicleControls;

  constructor(protected vehicleControlsService: VehicleControlsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleControlsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
