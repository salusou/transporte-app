import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICities } from '../cities.model';
import { CitiesService } from '../service/cities.service';

@Component({
  templateUrl: './cities-delete-dialog.component.html',
})
export class CitiesDeleteDialogComponent {
  cities?: ICities;

  constructor(protected citiesService: CitiesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.citiesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
