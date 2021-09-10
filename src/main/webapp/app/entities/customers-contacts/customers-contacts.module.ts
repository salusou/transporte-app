import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CustomersContactsComponent } from './list/customers-contacts.component';
import { CustomersContactsDetailComponent } from './detail/customers-contacts-detail.component';
import { CustomersContactsUpdateComponent } from './update/customers-contacts-update.component';
import { CustomersContactsDeleteDialogComponent } from './delete/customers-contacts-delete-dialog.component';
import { CustomersContactsRoutingModule } from './route/customers-contacts-routing.module';

@NgModule({
  imports: [SharedModule, CustomersContactsRoutingModule],
  declarations: [
    CustomersContactsComponent,
    CustomersContactsDetailComponent,
    CustomersContactsUpdateComponent,
    CustomersContactsDeleteDialogComponent,
  ],
  entryComponents: [CustomersContactsDeleteDialogComponent],
})
export class CustomersContactsModule {}
