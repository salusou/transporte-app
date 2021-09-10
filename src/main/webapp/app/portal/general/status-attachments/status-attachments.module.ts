import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatusAttachmentsPortalComponent } from './list/status-attachments.component';
import { StatusAttachmentsDetailPortalComponent } from './detail/status-attachments-detail.component';
import { StatusAttachmentsUpdatePortalComponent } from './update/status-attachments-update.component';
import { StatusAttachmentsDeleteDialogPortalComponent } from './delete/status-attachments-delete-dialog.component';
import { StatusAttachmentsRoutingModule } from './route/status-attachments-routing.module';

@NgModule({
  imports: [SharedModule, StatusAttachmentsRoutingModule],
  declarations: [
    StatusAttachmentsPortalComponent,
    StatusAttachmentsDetailPortalComponent,
    StatusAttachmentsUpdatePortalComponent,
    StatusAttachmentsDeleteDialogPortalComponent,
  ],
  entryComponents: [StatusAttachmentsDeleteDialogPortalComponent],
})
export class StatusAttachmentsModule {}
