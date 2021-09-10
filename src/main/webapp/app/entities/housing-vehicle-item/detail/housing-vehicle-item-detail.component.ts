import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHousingVehicleItem } from '../housing-vehicle-item.model';

@Component({
  selector: 'jhi-housing-vehicle-item-detail',
  templateUrl: './housing-vehicle-item-detail.component.html',
})
export class HousingVehicleItemDetailComponent implements OnInit {
  housingVehicleItem: IHousingVehicleItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ housingVehicleItem }) => {
      this.housingVehicleItem = housingVehicleItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
