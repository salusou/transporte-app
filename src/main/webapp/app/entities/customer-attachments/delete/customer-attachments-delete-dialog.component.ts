import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerAttachments } from '../customer-attachments.model';
import { CustomerAttachmentsService } from '../service/customer-attachments.service';

@Component({
  templateUrl: './customer-attachments-delete-dialog.component.html',
})
export class CustomerAttachmentsDeleteDialogComponent {
  customerAttachments?: ICustomerAttachments;

  constructor(protected customerAttachmentsService: CustomerAttachmentsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerAttachmentsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
