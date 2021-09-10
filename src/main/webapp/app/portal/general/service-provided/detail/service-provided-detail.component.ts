import { Component, OnInit } from '@angular/core';
import { ServiceProvidedDetailComponent } from '../../../../entities/service-provided/detail/service-provided-detail.component';

@Component({
  selector: 'jhi-service-provided-detail-portal',
  templateUrl: './service-provided-detail.component.html',
})
export class ServiceProvidedDetailPortalComponent extends ServiceProvidedDetailComponent implements OnInit {}
