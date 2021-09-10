import { Component, OnInit } from '@angular/core';
import { ServiceProvidedComponent } from '../../../../entities/service-provided/list/service-provided.component';

@Component({
  selector: 'jhi-service-provided-portal-portal',
  templateUrl: './service-provided.component.html',
})
export class ServiceProvidedPortalComponent extends ServiceProvidedComponent implements OnInit {}
