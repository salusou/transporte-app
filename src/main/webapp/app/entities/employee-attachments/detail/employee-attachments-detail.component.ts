import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeAttachments } from '../employee-attachments.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-employee-attachments-detail',
  templateUrl: './employee-attachments-detail.component.html',
})
export class EmployeeAttachmentsDetailComponent implements OnInit {
  employeeAttachments: IEmployeeAttachments | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeAttachments }) => {
      this.employeeAttachments = employeeAttachments;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
