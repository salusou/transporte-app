import { Component, OnInit } from '@angular/core';
import { ServiceProvidedUpdateComponent } from '../../../../entities/service-provided/update/service-provided-update.component';

@Component({
  selector: 'jhi-service-provided-update-portal',
  templateUrl: './service-provided-update.component.html',
})
export class ServiceProvidedUpdatePortalComponent extends ServiceProvidedUpdateComponent implements OnInit {}
