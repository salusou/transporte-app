import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployeeAttachments } from '../employee-attachments.model';
import { EmployeeAttachmentsService } from '../service/employee-attachments.service';

@Component({
  templateUrl: './employee-attachments-delete-dialog.component.html',
})
export class EmployeeAttachmentsDeleteDialogComponent {
  employeeAttachments?: IEmployeeAttachments;

  constructor(protected employeeAttachmentsService: EmployeeAttachmentsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeAttachmentsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
