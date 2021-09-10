import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatusAttachmentsComponent } from './list/status-attachments.component';
import { StatusAttachmentsDetailComponent } from './detail/status-attachments-detail.component';
import { StatusAttachmentsUpdateComponent } from './update/status-attachments-update.component';
import { StatusAttachmentsDeleteDialogComponent } from './delete/status-attachments-delete-dialog.component';
import { StatusAttachmentsRoutingModule } from './route/status-attachments-routing.module';

@NgModule({
  imports: [SharedModule, StatusAttachmentsRoutingModule],
  declarations: [
    StatusAttachmentsComponent,
    StatusAttachmentsDetailComponent,
    StatusAttachmentsUpdateComponent,
    StatusAttachmentsDeleteDialogComponent,
  ],
  entryComponents: [StatusAttachmentsDeleteDialogComponent],
})
export class StatusAttachmentsModule {}
