import { Component, OnInit } from '@angular/core';
import { StatusUpdateComponent } from '../../../../entities/status/update/status-update.component';

@Component({
  selector: 'jhi-status-update-portal',
  templateUrl: './status-update.component.html',
})
export class StatusUpdatePortalComponent extends StatusUpdateComponent implements OnInit {}
