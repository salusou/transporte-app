import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleControls } from '../vehicle-controls.model';

@Component({
  selector: 'jhi-vehicle-controls-detail',
  templateUrl: './vehicle-controls-detail.component.html',
})
export class VehicleControlsDetailComponent implements OnInit {
  vehicleControls: IVehicleControls | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControls }) => {
      this.vehicleControls = vehicleControls;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
