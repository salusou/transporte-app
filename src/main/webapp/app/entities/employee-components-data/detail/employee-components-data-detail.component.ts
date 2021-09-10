import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeComponentsData } from '../employee-components-data.model';

@Component({
  selector: 'jhi-employee-components-data-detail',
  templateUrl: './employee-components-data-detail.component.html',
})
export class EmployeeComponentsDataDetailComponent implements OnInit {
  employeeComponentsData: IEmployeeComponentsData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeComponentsData }) => {
      this.employeeComponentsData = employeeComponentsData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
