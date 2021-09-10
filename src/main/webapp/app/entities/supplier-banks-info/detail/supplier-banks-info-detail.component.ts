import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierBanksInfo } from '../supplier-banks-info.model';

@Component({
  selector: 'jhi-supplier-banks-info-detail',
  templateUrl: './supplier-banks-info-detail.component.html',
})
export class SupplierBanksInfoDetailComponent implements OnInit {
  supplierBanksInfo: ISupplierBanksInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierBanksInfo }) => {
      this.supplierBanksInfo = supplierBanksInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
