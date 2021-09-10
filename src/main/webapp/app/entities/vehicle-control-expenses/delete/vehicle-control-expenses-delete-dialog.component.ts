import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleControlExpenses } from '../vehicle-control-expenses.model';
import { VehicleControlExpensesService } from '../service/vehicle-control-expenses.service';

@Component({
  templateUrl: './vehicle-control-expenses-delete-dialog.component.html',
})
export class VehicleControlExpensesDeleteDialogComponent {
  vehicleControlExpenses?: IVehicleControlExpenses;

  constructor(protected vehicleControlExpensesService: VehicleControlExpensesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleControlExpensesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
