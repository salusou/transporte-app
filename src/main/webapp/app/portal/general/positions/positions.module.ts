import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PositionsUpdatePortalComponent } from './update/positions-update.component';
import { PositionsDeleteDialogPortalComponent } from './delete/positions-delete-dialog.component';
import { PositionsRoutingModule } from './route/positions-routing.module';
import { PositionsPortalComponent } from './list/positions.component';
import { PositionsDetailPortalComponent } from './detail/positions-detail.component';

@NgModule({
  imports: [SharedModule, PositionsRoutingModule],
  declarations: [
    PositionsPortalComponent,
    PositionsDetailPortalComponent,
    PositionsUpdatePortalComponent,
    PositionsDeleteDialogPortalComponent,
  ],
  entryComponents: [PositionsDeleteDialogPortalComponent],
})
export class PositionsModule {}
