import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmployeeAttachmentsComponent } from './list/employee-attachments.component';
import { EmployeeAttachmentsDetailComponent } from './detail/employee-attachments-detail.component';
import { EmployeeAttachmentsUpdateComponent } from './update/employee-attachments-update.component';
import { EmployeeAttachmentsDeleteDialogComponent } from './delete/employee-attachments-delete-dialog.component';
import { EmployeeAttachmentsRoutingModule } from './route/employee-attachments-routing.module';

@NgModule({
  imports: [SharedModule, EmployeeAttachmentsRoutingModule],
  declarations: [
    EmployeeAttachmentsComponent,
    EmployeeAttachmentsDetailComponent,
    EmployeeAttachmentsUpdateComponent,
    EmployeeAttachmentsDeleteDialogComponent,
  ],
  entryComponents: [EmployeeAttachmentsDeleteDialogComponent],
})
export class EmployeeAttachmentsModule {}
