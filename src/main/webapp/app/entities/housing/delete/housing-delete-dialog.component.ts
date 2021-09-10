import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHousing } from '../housing.model';
import { HousingService } from '../service/housing.service';

@Component({
  templateUrl: './housing-delete-dialog.component.html',
})
export class HousingDeleteDialogComponent {
  housing?: IHousing;

  constructor(protected housingService: HousingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.housingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
