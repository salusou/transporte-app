import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CustomersGroupsComponent } from './list/customers-groups.component';
import { CustomersGroupsDetailComponent } from './detail/customers-groups-detail.component';
import { CustomersGroupsUpdateComponent } from './update/customers-groups-update.component';
import { CustomersGroupsDeleteDialogComponent } from './delete/customers-groups-delete-dialog.component';
import { CustomersGroupsRoutingModule } from './route/customers-groups-routing.module';

@NgModule({
  imports: [SharedModule, CustomersGroupsRoutingModule],
  declarations: [
    CustomersGroupsComponent,
    CustomersGroupsDetailComponent,
    CustomersGroupsUpdateComponent,
    CustomersGroupsDeleteDialogComponent,
  ],
  entryComponents: [CustomersGroupsDeleteDialogComponent],
})
export class CustomersGroupsModule {}
