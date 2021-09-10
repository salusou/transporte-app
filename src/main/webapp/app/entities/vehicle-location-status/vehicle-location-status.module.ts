import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleLocationStatusComponent } from './list/vehicle-location-status.component';
import { VehicleLocationStatusDetailComponent } from './detail/vehicle-location-status-detail.component';
import { VehicleLocationStatusUpdateComponent } from './update/vehicle-location-status-update.component';
import { VehicleLocationStatusDeleteDialogComponent } from './delete/vehicle-location-status-delete-dialog.component';
import { VehicleLocationStatusRoutingModule } from './route/vehicle-location-status-routing.module';

@NgModule({
  imports: [SharedModule, VehicleLocationStatusRoutingModule],
  declarations: [
    VehicleLocationStatusComponent,
    VehicleLocationStatusDetailComponent,
    VehicleLocationStatusUpdateComponent,
    VehicleLocationStatusDeleteDialogComponent,
  ],
  entryComponents: [VehicleLocationStatusDeleteDialogComponent],
})
export class VehicleLocationStatusModule {}
