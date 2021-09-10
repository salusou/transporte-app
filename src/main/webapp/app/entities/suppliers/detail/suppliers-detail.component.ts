import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuppliers } from '../suppliers.model';

@Component({
  selector: 'jhi-suppliers-detail',
  templateUrl: './suppliers-detail.component.html',
})
export class SuppliersDetailComponent implements OnInit {
  suppliers: ISuppliers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suppliers }) => {
      this.suppliers = suppliers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
