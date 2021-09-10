import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleInspectionsImagensComponent } from './list/vehicle-inspections-imagens.component';
import { VehicleInspectionsImagensDetailComponent } from './detail/vehicle-inspections-imagens-detail.component';
import { VehicleInspectionsImagensUpdateComponent } from './update/vehicle-inspections-imagens-update.component';
import { VehicleInspectionsImagensDeleteDialogComponent } from './delete/vehicle-inspections-imagens-delete-dialog.component';
import { VehicleInspectionsImagensRoutingModule } from './route/vehicle-inspections-imagens-routing.module';

@NgModule({
  imports: [SharedModule, VehicleInspectionsImagensRoutingModule],
  declarations: [
    VehicleInspectionsImagensComponent,
    VehicleInspectionsImagensDetailComponent,
    VehicleInspectionsImagensUpdateComponent,
    VehicleInspectionsImagensDeleteDialogComponent,
  ],
  entryComponents: [VehicleInspectionsImagensDeleteDialogComponent],
})
export class VehicleInspectionsImagensModule {}
