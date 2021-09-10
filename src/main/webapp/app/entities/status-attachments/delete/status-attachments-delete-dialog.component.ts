import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatusAttachments } from '../status-attachments.model';
import { StatusAttachmentsService } from '../service/status-attachments.service';

@Component({
  templateUrl: './status-attachments-delete-dialog.component.html',
})
export class StatusAttachmentsDeleteDialogComponent {
  statusAttachments?: IStatusAttachments;

  constructor(protected statusAttachmentsService: StatusAttachmentsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statusAttachmentsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
