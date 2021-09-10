import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceProvidedComponent } from '../list/service-provided.component';
import { ServiceProvidedDetailComponent } from '../detail/service-provided-detail.component';
import { ServiceProvidedUpdateComponent } from '../update/service-provided-update.component';
import { ServiceProvidedRoutingResolveService } from './service-provided-routing-resolve.service';

const serviceProvidedRoute: Routes = [
  {
    path: '',
    component: ServiceProvidedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceProvidedDetailComponent,
    resolve: {
      serviceProvided: ServiceProvidedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceProvidedUpdateComponent,
    resolve: {
      serviceProvided: ServiceProvidedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceProvidedUpdateComponent,
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
