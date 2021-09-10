import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleInspections } from '../vehicle-inspections.model';
import { VehicleInspectionsService } from '../service/vehicle-inspections.service';

@Component({
  templateUrl: './vehicle-inspections-delete-dialog.component.html',
})
export class VehicleInspectionsDeleteDialogComponent {
  vehicleInspections?: IVehicleInspections;

  constructor(protected vehicleInspectionsService: VehicleInspectionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleInspectionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
