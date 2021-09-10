import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';
import { VehicleInspectionsImagensService } from '../service/vehicle-inspections-imagens.service';

@Component({
  templateUrl: './vehicle-inspections-imagens-delete-dialog.component.html',
})
export class VehicleInspectionsImagensDeleteDialogComponent {
  vehicleInspectionsImagens?: IVehicleInspectionsImagens;

  constructor(protected vehicleInspectionsImagensService: VehicleInspectionsImagensService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleInspectionsImagensService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
