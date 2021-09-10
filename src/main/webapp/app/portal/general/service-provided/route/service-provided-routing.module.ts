import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceProvidedPortalComponent } from '../list/service-provided.component';
import { ServiceProvidedDetailPortalComponent } from '../detail/service-provided-detail.component';
import { ServiceProvidedUpdatePortalComponent } from '../update/service-provided-update.component';
import { ServiceProvidedRoutingResolveService } from './service-provided-routing-resolve.service';

const serviceProvidedRoute: Routes = [
  {
    path: '',
    component: ServiceProvidedPortalComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceProvidedDetailPortalComponent,
    resolve: {
      serviceProvided: ServiceProvidedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceProvidedUpdatePortalComponent,
    resolve: {
      serviceProvided: ServiceProvidedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceProvidedUpdatePortalComponent,
    resolve: {
      serviceProvided: ServiceProvidedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serviceProvidedRoute)],
  exports: [RouterModule],
})
export class ServiceProvidedRoutingModule {}
