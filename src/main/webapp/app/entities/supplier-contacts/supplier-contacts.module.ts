import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SupplierContactsComponent } from './list/supplier-contacts.component';
import { SupplierContactsDetailComponent } from './detail/supplier-contacts-detail.component';
import { SupplierContactsUpdateComponent } from './update/supplier-contacts-update.component';
import { SupplierContactsDeleteDialogComponent } from './delete/supplier-contacts-delete-dialog.component';
import { SupplierContactsRoutingModule } from './route/supplier-contacts-routing.module';

@NgModule({
  imports: [SharedModule, SupplierContactsRoutingModule],
  declarations: [
    SupplierContactsComponent,
    SupplierContactsDetailComponent,
    SupplierContactsUpdateComponent,
    SupplierContactsDeleteDialogComponent,
  ],
  entryComponents: [SupplierContactsDeleteDialogComponent],
})
export class SupplierContactsModule {}
