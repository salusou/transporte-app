import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHousing } from '../housing.model';

@Component({
  selector: 'jhi-housing-detail',
  templateUrl: './housing-detail.component.html',
})
export class HousingDetailComponent implements OnInit {
  housing: IHousing | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ housing }) => {
      this.housing = housing;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
