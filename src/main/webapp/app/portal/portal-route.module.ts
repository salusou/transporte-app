import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild([
      {
        path: 'general',
        data: { pageTitle: 'global.menu.entities.general' },
        loadChildren: () => import('./general/general-routes.module').then(m => m.GeneralRoutesModule),
      },
    ]),
  ],
})
export class PortalRouteModule {}
