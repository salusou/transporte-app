import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStateProvinces } from '../state-provinces.model';

@Component({
  selector: 'jhi-state-provinces-detail',
  templateUrl: './state-provinces-detail.component.html',
})
export class StateProvincesDetailComponent implements OnInit {
  stateProvinces: IStateProvinces | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stateProvinces }) => {
      this.stateProvinces = stateProvinces;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
