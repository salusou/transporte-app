import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHousingVehicleItem } from '../housing-vehicle-item.model';
import { HousingVehicleItemService } from '../service/housing-vehicle-item.service';

@Component({
  templateUrl: './housing-vehicle-item-delete-dialog.component.html',
})
export class HousingVehicleItemDeleteDialogComponent {
  housingVehicleItem?: IHousingVehicleItem;

  constructor(protected housingVehicleItemService: HousingVehicleItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.housingVehicleItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
