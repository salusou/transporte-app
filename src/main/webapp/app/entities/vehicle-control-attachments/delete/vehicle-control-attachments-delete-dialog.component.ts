import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleControlAttachments } from '../vehicle-control-attachments.model';
import { VehicleControlAttachmentsService } from '../service/vehicle-control-attachments.service';

@Component({
  templateUrl: './vehicle-control-attachments-delete-dialog.component.html',
})
export class VehicleControlAttachmentsDeleteDialogComponent {
  vehicleControlAttachments?: IVehicleControlAttachments;

  constructor(protected vehicleControlAttachmentsService: VehicleControlAttachmentsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleControlAttachmentsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
