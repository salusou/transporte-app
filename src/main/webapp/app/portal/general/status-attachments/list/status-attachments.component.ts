import { Component, OnInit } from '@angular/core';
import { StatusAttachmentsComponent } from '../../../../entities/status-attachments/list/status-attachments.component';

@Component({
  selector: 'jhi-status-attachments-portal',
  templateUrl: './status-attachments.component.html',
})
export class StatusAttachmentsPortalComponent extends StatusAttachmentsComponent implements OnInit {}
