import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanies } from '../companies.model';

@Component({
  selector: 'jhi-companies-detail',
  templateUrl: './companies-detail.component.html',
})
export class CompaniesDetailComponent implements OnInit {
  companies: ICompanies | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companies }) => {
      this.companies = companies;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
