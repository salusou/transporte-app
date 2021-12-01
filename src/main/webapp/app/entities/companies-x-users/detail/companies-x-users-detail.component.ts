import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompaniesXUsers } from '../companies-x-users.model';

@Component({
  selector: 'jhi-companies-x-users-detail',
  templateUrl: './companies-x-users-detail.component.html',
})
export class CompaniesXUsersDetailComponent implements OnInit {
  companiesXUsers: ICompaniesXUsers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companiesXUsers }) => {
      this.companiesXUsers = companiesXUsers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
