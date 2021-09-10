import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomersContacts } from '../customers-contacts.model';

@Component({
  selector: 'jhi-customers-contacts-detail',
  templateUrl: './customers-contacts-detail.component.html',
})
export class CustomersContactsDetailComponent implements OnInit {
  customersContacts: ICustomersContacts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customersContacts }) => {
      this.customersContacts = customersContacts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
