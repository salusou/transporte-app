import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleControlAttachmentsComponent } from './list/vehicle-control-attachments.component';
import { VehicleControlAttachmentsDetailComponent } from './detail/vehicle-control-attachments-detail.component';
import { VehicleControlAttachmentsUpdateComponent } from './update/vehicle-control-attachments-update.component';
import { VehicleControlAttachmentsDeleteDialogComponent } from './delete/vehicle-control-attachments-delete-dialog.component';
import { VehicleControlAttachmentsRoutingModule } from './route/vehicle-control-attachments-routing.module';

@NgModule({
  imports: [SharedModule, VehicleControlAttachmentsRoutingModule],
  declarations: [
    VehicleControlAttachmentsComponent,
    VehicleControlAttachmentsDetailComponent,
    VehicleControlAttachmentsUpdateComponent,
    VehicleControlAttachmentsDeleteDialogComponent,
  ],
  entryComponents: [VehicleControlAttachmentsDeleteDialogComponent],
})
export class VehicleControlAttachmentsModule {}
