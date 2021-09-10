import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StateProvincesComponent } from './list/state-provinces.component';
import { StateProvincesDetailComponent } from './detail/state-provinces-detail.component';
import { StateProvincesUpdateComponent } from './update/state-provinces-update.component';
import { StateProvincesDeleteDialogComponent } from './delete/state-provinces-delete-dialog.component';
import { StateProvincesRoutingModule } from './route/state-provinces-routing.module';

@NgModule({
  imports: [SharedModule, StateProvincesRoutingModule],
  declarations: [
    StateProvincesComponent,
    StateProvincesDetailComponent,
    StateProvincesUpdateComponent,
    StateProvincesDeleteDialogComponent,
  ],
  entryComponents: [StateProvincesDeleteDialogComponent],
})
export class StateProvincesModule {}
