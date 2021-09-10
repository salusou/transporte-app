import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatusPortalComponent } from './list/status.component';
import { StatusDetailPortalComponent } from './detail/status-detail.component';
import { StatusUpdatePortalComponent } from './update/status-update.component';
import { StatusDeleteDialogPortalComponent } from './delete/status-delete-dialog.component';
import { StatusRoutingModule } from './route/status-routing.module';

@NgModule({
  imports: [SharedModule, StatusRoutingModule],
  declarations: [StatusPortalComponent, StatusDetailPortalComponent, StatusUpdatePortalComponent, StatusDeleteDialogPortalComponent],
  entryComponents: [StatusDeleteDialogPortalComponent],
})
export class StatusModule {}
