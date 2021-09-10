import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleControlItemComponent } from './list/vehicle-control-item.component';
import { VehicleControlItemDetailComponent } from './detail/vehicle-control-item-detail.component';
import { VehicleControlItemUpdateComponent } from './update/vehicle-control-item-update.component';
import { VehicleControlItemDeleteDialogComponent } from './delete/vehicle-control-item-delete-dialog.component';
import { VehicleControlItemRoutingModule } from './route/vehicle-control-item-routing.module';

@NgModule({
  imports: [SharedModule, VehicleControlItemRoutingModule],
  declarations: [
    VehicleControlItemComponent,
    VehicleControlItemDetailComponent,
    VehicleControlItemUpdateComponent,
    VehicleControlItemDeleteDialogComponent,
  ],
  entryComponents: [VehicleControlItemDeleteDialogComponent],
})
export class VehicleControlItemModule {}
