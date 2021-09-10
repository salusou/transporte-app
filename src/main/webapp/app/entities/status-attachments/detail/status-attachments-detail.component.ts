import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusAttachments } from '../status-attachments.model';

@Component({
  selector: 'jhi-status-attachments-detail',
  templateUrl: './status-attachments-detail.component.html',
})
export class StatusAttachmentsDetailComponent implements OnInit {
  statusAttachments: IStatusAttachments | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusAttachments }) => {
      this.statusAttachments = statusAttachments;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
