import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleInspections } from '../vehicle-inspections.model';

@Component({
  selector: 'jhi-vehicle-inspections-detail',
  templateUrl: './vehicle-inspections-detail.component.html',
})
export class VehicleInspectionsDetailComponent implements OnInit {
  vehicleInspections: IVehicleInspections | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleInspections }) => {
      this.vehicleInspections = vehicleInspections;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
