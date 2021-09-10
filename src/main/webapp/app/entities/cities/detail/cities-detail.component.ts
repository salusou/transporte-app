import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICities } from '../cities.model';

@Component({
  selector: 'jhi-cities-detail',
  templateUrl: './cities-detail.component.html',
})
export class CitiesDetailComponent implements OnInit {
  cities: ICities | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cities }) => {
      this.cities = cities;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
