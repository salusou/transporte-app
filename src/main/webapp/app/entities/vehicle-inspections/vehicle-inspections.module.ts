import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleInspectionsComponent } from './list/vehicle-inspections.component';
import { VehicleInspectionsDetailComponent } from './detail/vehicle-inspections-detail.component';
import { VehicleInspectionsUpdateComponent } from './update/vehicle-inspections-update.component';
import { VehicleInspectionsDeleteDialogComponent } from './delete/vehicle-inspections-delete-dialog.component';
import { VehicleInspectionsRoutingModule } from './route/vehicle-inspections-routing.module';

@NgModule({
  imports: [SharedModule, VehicleInspectionsRoutingModule],
  declarations: [
    VehicleInspectionsComponent,
    VehicleInspectionsDetailComponent,
    VehicleInspectionsUpdateComponent,
    VehicleInspectionsDeleteDialogComponent,
  ],
  entryComponents: [VehicleInspectionsDeleteDialogComponent],
})
export class VehicleInspectionsModule {}
