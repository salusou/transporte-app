import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleControlItem } from '../vehicle-control-item.model';

@Component({
  selector: 'jhi-vehicle-control-item-detail',
  templateUrl: './vehicle-control-item-detail.component.html',
})
export class VehicleControlItemDetailComponent implements OnInit {
  vehicleControlItem: IVehicleControlItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlItem }) => {
      this.vehicleControlItem = vehicleControlItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
