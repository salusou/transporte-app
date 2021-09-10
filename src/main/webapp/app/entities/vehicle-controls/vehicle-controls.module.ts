import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleControlsComponent } from './list/vehicle-controls.component';
import { VehicleControlsDetailComponent } from './detail/vehicle-controls-detail.component';
import { VehicleControlsUpdateComponent } from './update/vehicle-controls-update.component';
import { VehicleControlsDeleteDialogComponent } from './delete/vehicle-controls-delete-dialog.component';
import { VehicleControlsRoutingModule } from './route/vehicle-controls-routing.module';

@NgModule({
  imports: [SharedModule, VehicleControlsRoutingModule],
  declarations: [
    VehicleControlsComponent,
    VehicleControlsDetailComponent,
    VehicleControlsUpdateComponent,
    VehicleControlsDeleteDialogComponent,
  ],
  entryComponents: [VehicleControlsDeleteDialogComponent],
})
export class VehicleControlsModule {}
