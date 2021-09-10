import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleLocationStatus } from '../vehicle-location-status.model';

@Component({
  selector: 'jhi-vehicle-location-status-detail',
  templateUrl: './vehicle-location-status-detail.component.html',
})
export class VehicleLocationStatusDetailComponent implements OnInit {
  vehicleLocationStatus: IVehicleLocationStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleLocationStatus }) => {
      this.vehicleLocationStatus = vehicleLocationStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
