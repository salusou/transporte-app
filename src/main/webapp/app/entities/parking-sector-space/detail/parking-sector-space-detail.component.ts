import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParkingSectorSpace } from '../parking-sector-space.model';

@Component({
  selector: 'jhi-parking-sector-space-detail',
  templateUrl: './parking-sector-space-detail.component.html',
})
export class ParkingSectorSpaceDetailComponent implements OnInit {
  parkingSectorSpace: IParkingSectorSpace | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parkingSectorSpace }) => {
      this.parkingSectorSpace = parkingSectorSpace;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
