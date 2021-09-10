import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleControlBillingComponent } from './list/vehicle-control-billing.component';
import { VehicleControlBillingDetailComponent } from './detail/vehicle-control-billing-detail.component';
import { VehicleControlBillingUpdateComponent } from './update/vehicle-control-billing-update.component';
import { VehicleControlBillingDeleteDialogComponent } from './delete/vehicle-control-billing-delete-dialog.component';
import { VehicleControlBillingRoutingModule } from './route/vehicle-control-billing-routing.module';

@NgModule({
  imports: [SharedModule, VehicleControlBillingRoutingModule],
  declarations: [
    VehicleControlBillingComponent,
    VehicleControlBillingDetailComponent,
    VehicleControlBillingUpdateComponent,
    VehicleControlBillingDeleteDialogComponent,
  ],
  entryComponents: [VehicleControlBillingDeleteDialogComponent],
})
export class VehicleControlBillingModule {}
