import { Component, OnInit } from '@angular/core';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { StatusAttachmentsUpdateComponent } from '../../../../entities/status-attachments/update/status-attachments-update.component';

@Component({
  selector: 'jhi-status-attachments-update-portal',
  templateUrl: './status-attachments-update.component.html',
})
export class StatusAttachmentsUpdatePortalComponent extends StatusAttachmentsUpdateComponent implements OnInit {
  trackAffiliatesById(index: number, item: IAffiliates): number {
    return item.id!;
  }
}
