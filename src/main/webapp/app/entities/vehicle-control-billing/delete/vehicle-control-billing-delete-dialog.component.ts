import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleControlBilling } from '../vehicle-control-billing.model';
import { VehicleControlBillingService } from '../service/vehicle-control-billing.service';

@Component({
  templateUrl: './vehicle-control-billing-delete-dialog.component.html',
})
export class VehicleControlBillingDeleteDialogComponent {
  vehicleControlBilling?: IVehicleControlBilling;

  constructor(protected vehicleControlBillingService: VehicleControlBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleControlBillingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
