import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleControlAttachments } from '../vehicle-control-attachments.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-vehicle-control-attachments-detail',
  templateUrl: './vehicle-control-attachments-detail.component.html',
})
export class VehicleControlAttachmentsDetailComponent implements OnInit {
  vehicleControlAttachments: IVehicleControlAttachments | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleControlAttachments }) => {
      this.vehicleControlAttachments = vehicleControlAttachments;
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
