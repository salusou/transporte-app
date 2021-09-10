import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmployeeComponentsDataComponent } from './list/employee-components-data.component';
import { EmployeeComponentsDataDetailComponent } from './detail/employee-components-data-detail.component';
import { EmployeeComponentsDataUpdateComponent } from './update/employee-components-data-update.component';
import { EmployeeComponentsDataDeleteDialogComponent } from './delete/employee-components-data-delete-dialog.component';
import { EmployeeComponentsDataRoutingModule } from './route/employee-components-data-routing.module';

@NgModule({
  imports: [SharedModule, EmployeeComponentsDataRoutingModule],
  declarations: [
    EmployeeComponentsDataComponent,
    EmployeeComponentsDataDetailComponent,
    EmployeeComponentsDataUpdateComponent,
    EmployeeComponentsDataDeleteDialogComponent,
  ],
  entryComponents: [EmployeeComponentsDataDeleteDialogComponent],
})
export class EmployeeComponentsDataModule {}
