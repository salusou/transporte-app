import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployeeComponentsData } from '../employee-components-data.model';
import { EmployeeComponentsDataService } from '../service/employee-components-data.service';

@Component({
  templateUrl: './employee-components-data-delete-dialog.component.html',
})
export class EmployeeComponentsDataDeleteDialogComponent {
  employeeComponentsData?: IEmployeeComponentsData;

  constructor(protected employeeComponentsDataService: EmployeeComponentsDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeComponentsDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
