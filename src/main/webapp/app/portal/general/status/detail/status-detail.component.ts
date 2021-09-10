import { Component, OnInit } from '@angular/core';
import { StatusDetailComponent } from '../../../../entities/status/detail/status-detail.component';

@Component({
  selector: 'jhi-status-detail-portal',
  templateUrl: './status-detail.component.html',
})
export class StatusDetailPortalComponent extends StatusDetailComponent implements OnInit {}
