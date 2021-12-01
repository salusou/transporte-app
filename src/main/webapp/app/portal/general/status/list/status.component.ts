import { Component, OnInit } from '@angular/core';
import { StatusComponent } from '../../../../entities/status/list/status.component';

@Component({
  selector: 'jhi-status-portal',
  templateUrl: './status.component.html',
})
export class StatusPortalComponent extends StatusComponent implements OnInit {}
