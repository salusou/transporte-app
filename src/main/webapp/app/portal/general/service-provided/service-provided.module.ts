import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceProvidedPortalComponent } from './list/service-provided.component';
import { ServiceProvidedDetailPortalComponent } from './detail/service-provided-detail.component';
import { ServiceProvidedUpdatePortalComponent } from './update/service-provided-update.component';
import { ServiceProvidedDeleteDialogPortalComponent } from './delete/service-provided-delete-dialog.component';
import { ServiceProvidedRoutingModule } from './route/service-provided-routing.module';

@NgModule({
  imports: [SharedModule, ServiceProvidedRoutingModule],
  declarations: [
    ServiceProvidedPortalComponent,
    ServiceProvidedDetailPortalComponent,
    ServiceProvidedUpdatePortalComponent,
    ServiceProvidedDeleteDialogPortalComponent,
  ],
  entryComponents: [ServiceProvidedDeleteDialogPortalComponent],
})
export class ServiceProvidedModule {}
