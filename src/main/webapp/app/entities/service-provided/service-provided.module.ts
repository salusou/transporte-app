import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceProvidedComponent } from './list/service-provided.component';
import { ServiceProvidedDetailComponent } from './detail/service-provided-detail.component';
import { ServiceProvidedUpdateComponent } from './update/service-provided-update.component';
import { ServiceProvidedDeleteDialogComponent } from './delete/service-provided-delete-dialog.component';
import { ServiceProvidedRoutingModule } from './route/service-provided-routing.module';

@NgModule({
  imports: [SharedModule, ServiceProvidedRoutingModule],
  declarations: [
    ServiceProvidedComponent,
    ServiceProvidedDetailComponent,
    ServiceProvidedUpdateComponent,
    ServiceProvidedDeleteDialogComponent,
  ],
  entryComponents: [ServiceProvidedDeleteDialogComponent],
})
export class ServiceProvidedModule {}
