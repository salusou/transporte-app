import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleControlHistoryComponent } from './list/vehicle-control-history.component';
import { VehicleControlHistoryDetailComponent } from './detail/vehicle-control-history-detail.component';
import { VehicleControlHistoryUpdateComponent } from './update/vehicle-control-history-update.component';
import { VehicleControlHistoryDeleteDialogComponent } from './delete/vehicle-control-history-delete-dialog.component';
import { VehicleControlHistoryRoutingModule } from './route/vehicle-control-history-routing.module';

@NgModule({
  imports: [SharedModule, VehicleControlHistoryRoutingModule],
  declarations: [
    VehicleControlHistoryComponent,
    VehicleControlHistoryDetailComponent,
    VehicleControlHistoryUpdateComponent,
    VehicleControlHistoryDeleteDialogComponent,
  ],
  entryComponents: [VehicleControlHistoryDeleteDialogComponent],
})
export class VehicleControlHistoryModule {}
