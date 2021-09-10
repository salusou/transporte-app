import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceProvided } from '../service-provided.model';
import { ServiceProvidedService } from '../service/service-provided.service';

@Component({
  templateUrl: './service-provided-delete-dialog.component.html',
})
export class ServiceProvidedDeleteDialogComponent {
  serviceProvided?: IServiceProvided;

  constructor(protected serviceProvidedService: ServiceProvidedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceProvidedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
