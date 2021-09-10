import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParkingSector } from '../parking-sector.model';

@Component({
  selector: 'jhi-parking-sector-detail',
  templateUrl: './parking-sector-detail.component.html',
})
export class ParkingSectorDetailComponent implements OnInit {
  parkingSector: IParkingSector | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parkingSector }) => {
      this.parkingSector = parkingSector;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
