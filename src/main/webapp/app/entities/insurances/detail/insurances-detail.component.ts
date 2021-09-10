import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInsurances } from '../insurances.model';

@Component({
  selector: 'jhi-insurances-detail',
  templateUrl: './insurances-detail.component.html',
})
export class InsurancesDetailComponent implements OnInit {
  insurances: IInsurances | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ insurances }) => {
      this.insurances = insurances;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
