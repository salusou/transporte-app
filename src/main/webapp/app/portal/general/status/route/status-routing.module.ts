import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusPortalComponent } from '../list/status.component';
import { StatusDetailPortalComponent } from '../detail/status-detail.component';
import { StatusUpdatePortalComponent } from '../update/status-update.component';
import { StatusRoutingResolveService } from './status-routing-resolve.service';

const statusRoute: Routes = [
  {
    path: '',
    component: StatusPortalComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusDetailPortalComponent,
    resolve: {
      status: StatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusUpdatePortalComponent,
    resolve: {
      status: StatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusUpdatePortalComponent,
    resolve: {
      status: StatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statusRoute)],
  exports: [RouterModule],
})
export class StatusRoutingModule {}
