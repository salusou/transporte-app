import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierContacts } from '../supplier-contacts.model';

@Component({
  selector: 'jhi-supplier-contacts-detail',
  templateUrl: './supplier-contacts-detail.component.html',
})
export class SupplierContactsDetailComponent implements OnInit {
  supplierContacts: ISupplierContacts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierContacts }) => {
      this.supplierContacts = supplierContacts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
