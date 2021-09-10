import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CustomerAttachmentsComponent } from './list/customer-attachments.component';
import { CustomerAttachmentsDetailComponent } from './detail/customer-attachments-detail.component';
import { CustomerAttachmentsUpdateComponent } from './update/customer-attachments-update.component';
import { CustomerAttachmentsDeleteDialogComponent } from './delete/customer-attachments-delete-dialog.component';
import { CustomerAttachmentsRoutingModule } from './route/customer-attachments-routing.module';

@NgModule({
  imports: [SharedModule, CustomerAttachmentsRoutingModule],
  declarations: [
    CustomerAttachmentsComponent,
    CustomerAttachmentsDetailComponent,
    CustomerAttachmentsUpdateComponent,
    CustomerAttachmentsDeleteDialogComponent,
  ],
  entryComponents: [CustomerAttachmentsDeleteDialogComponent],
})
export class CustomerAttachmentsModule {}
