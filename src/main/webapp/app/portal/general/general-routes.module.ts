import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'positions',
        data: { pageTitle: 'transporteApp.positions.home.title' },
        loadChildren: () => import('./positions/positions.module').then(m => m.PositionsModule),
      },
      {
        path: 'service-provided',
        data: { pageTitle: 'transporteApp.serviceProvided.home.title' },
        loadChildren: () => import('./service-provided/service-provided.module').then(m => m.ServiceProvidedModule),
      },
      {
        path: 'status-attachments',
        data: { pageTitle: 'transporteApp.statusAttachments.home.title' },
        loadChildren: () => import('./status-attachments/status-attachments.module').then(m => m.StatusAttachmentsModule),
      },
      {
        path: 'status',
        data: { pageTitle: 'transporteApp.status.home.title' },
        loadChildren: () => import('./status/status.module').then(m => m.StatusModule),
      },
    ]),
  ],
})
export class GeneralRoutesModule {}
