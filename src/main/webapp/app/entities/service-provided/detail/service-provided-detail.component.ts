import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceProvided } from '../service-provided.model';

@Component({
  selector: 'jhi-service-provided-detail',
  templateUrl: './service-provided-detail.component.html',
})
export class ServiceProvidedDetailComponent implements OnInit {
  serviceProvided: IServiceProvided | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceProvided }) => {
      this.serviceProvided = serviceProvided;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
