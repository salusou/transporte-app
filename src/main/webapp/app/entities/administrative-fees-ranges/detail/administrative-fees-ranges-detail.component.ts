import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdministrativeFeesRanges } from '../administrative-fees-ranges.model';

@Component({
  selector: 'jhi-administrative-fees-ranges-detail',
  templateUrl: './administrative-fees-ranges-detail.component.html',
})
export class AdministrativeFeesRangesDetailComponent implements OnInit {
  administrativeFeesRanges: IAdministrativeFeesRanges | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrativeFeesRanges }) => {
      this.administrativeFeesRanges = administrativeFeesRanges;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
