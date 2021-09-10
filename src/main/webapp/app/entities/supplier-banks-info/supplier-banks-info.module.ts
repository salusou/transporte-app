import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SupplierBanksInfoComponent } from './list/supplier-banks-info.component';
import { SupplierBanksInfoDetailComponent } from './detail/supplier-banks-info-detail.component';
import { SupplierBanksInfoUpdateComponent } from './update/supplier-banks-info-update.component';
import { SupplierBanksInfoDeleteDialogComponent } from './delete/supplier-banks-info-delete-dialog.component';
import { SupplierBanksInfoRoutingModule } from './route/supplier-banks-info-routing.module';

@NgModule({
  imports: [SharedModule, SupplierBanksInfoRoutingModule],
  declarations: [
    SupplierBanksInfoComponent,
    SupplierBanksInfoDetailComponent,
    SupplierBanksInfoUpdateComponent,
    SupplierBanksInfoDeleteDialogComponent,
  ],
  entryComponents: [SupplierBanksInfoDeleteDialogComponent],
})
export class SupplierBanksInfoModule {}
