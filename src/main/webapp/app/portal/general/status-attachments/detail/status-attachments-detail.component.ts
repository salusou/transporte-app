import { Component, OnInit } from '@angular/core';
import { StatusAttachmentsDetailComponent } from '../../../../entities/status-attachments/detail/status-attachments-detail.component';

@Component({
  selector: 'jhi-status-attachments-detail-portal',
  templateUrl: './status-attachments-detail.component.html',
})
export class StatusAttachmentsDetailPortalComponent extends StatusAttachmentsDetailComponent implements OnInit {}
