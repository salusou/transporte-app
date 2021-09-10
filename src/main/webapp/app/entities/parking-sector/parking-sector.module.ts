import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParkingSectorComponent } from './list/parking-sector.component';
import { ParkingSectorDetailComponent } from './detail/parking-sector-detail.component';
import { ParkingSectorUpdateComponent } from './update/parking-sector-update.component';
import { ParkingSectorDeleteDialogComponent } from './delete/parking-sector-delete-dialog.component';
import { ParkingSectorRoutingModule } from './route/parking-sector-routing.module';

@NgModule({
  imports: [SharedModule, ParkingSectorRoutingModule],
  declarations: [ParkingSectorComponent, ParkingSectorDetailComponent, ParkingSectorUpdateComponent, ParkingSectorDeleteDialogComponent],
  entryComponents: [ParkingSectorDeleteDialogComponent],
})
export class ParkingSectorModule {}
