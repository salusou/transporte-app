import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleControlExpenses } from '../vehicle-control-expenses.model';

@Component({
  selector: 'jhi-vehicle-control-expenses-detail',
  templateUrl: './vehicle-control-expenses-detail.component.html',
})
export class VehicleControlExpensesDetailComponent implements OnInit {
  vehicleControlExpenses: IVehicleControlExpenses | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlExpenses }) => {
      this.vehicleControlExpenses = vehicleControlExpenses;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
