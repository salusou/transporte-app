import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleControlHistory } from '../vehicle-control-history.model';

@Component({
  selector: 'jhi-vehicle-control-history-detail',
  templateUrl: './vehicle-control-history-detail.component.html',
})
export class VehicleControlHistoryDetailComponent implements OnInit {
  vehicleControlHistory: IVehicleControlHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlHistory }) => {
      this.vehicleControlHistory = vehicleControlHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
