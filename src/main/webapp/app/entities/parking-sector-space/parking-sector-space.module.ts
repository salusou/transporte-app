import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParkingSectorSpaceComponent } from './list/parking-sector-space.component';
import { ParkingSectorSpaceDetailComponent } from './detail/parking-sector-space-detail.component';
import { ParkingSectorSpaceUpdateComponent } from './update/parking-sector-space-update.component';
import { ParkingSectorSpaceDeleteDialogComponent } from './delete/parking-sector-space-delete-dialog.component';
import { ParkingSectorSpaceRoutingModule } from './route/parking-sector-space-routing.module';

@NgModule({
  imports: [SharedModule, ParkingSectorSpaceRoutingModule],
  declarations: [
    ParkingSectorSpaceComponent,
    ParkingSectorSpaceDetailComponent,
    ParkingSectorSpaceUpdateComponent,
    ParkingSectorSpaceDeleteDialogComponent,
  ],
  entryComponents: [ParkingSectorSpaceDeleteDialogComponent],
})
export class ParkingSectorSpaceModule {}
