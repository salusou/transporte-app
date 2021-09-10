import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStateProvinces } from '../state-provinces.model';
import { StateProvincesService } from '../service/state-provinces.service';

@Component({
  templateUrl: './state-provinces-delete-dialog.component.html',
})
export class StateProvincesDeleteDialogComponent {
  stateProvinces?: IStateProvinces;

  constructor(protected stateProvincesService: StateProvincesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stateProvincesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
