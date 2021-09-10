import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomersGroups } from '../customers-groups.model';

@Component({
  selector: 'jhi-customers-groups-detail',
  templateUrl: './customers-groups-detail.component.html',
})
export class CustomersGroupsDetailComponent implements OnInit {
  customersGroups: ICustomersGroups | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customersGroups }) => {
      this.customersGroups = customersGroups;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
