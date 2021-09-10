import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleControlBilling } from '../vehicle-control-billing.model';

@Component({
  selector: 'jhi-vehicle-control-billing-detail',
  templateUrl: './vehicle-control-billing-detail.component.html',
})
export class VehicleControlBillingDetailComponent implements OnInit {
  vehicleControlBilling: IVehicleControlBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlBilling }) => {
      this.vehicleControlBilling = vehicleControlBilling;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
