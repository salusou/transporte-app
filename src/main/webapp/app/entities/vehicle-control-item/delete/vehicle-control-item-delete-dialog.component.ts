import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleControlItem } from '../vehicle-control-item.model';
import { VehicleControlItemService } from '../service/vehicle-control-item.service';

@Component({
  templateUrl: './vehicle-control-item-delete-dialog.component.html',
})
export class VehicleControlItemDeleteDialogComponent {
  vehicleControlItem?: IVehicleControlItem;

  constructor(protected vehicleControlItemService: VehicleControlItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleControlItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
