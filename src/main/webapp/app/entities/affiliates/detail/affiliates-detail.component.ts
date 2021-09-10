import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAffiliates } from '../affiliates.model';

@Component({
  selector: 'jhi-affiliates-detail',
  templateUrl: './affiliates-detail.component.html',
})
export class AffiliatesDetailComponent implements OnInit {
  affiliates: IAffiliates | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affiliates }) => {
      this.affiliates = affiliates;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
