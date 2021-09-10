import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PositionsPortalComponent } from '../list/positions.component';
import { PositionsDetailPortalComponent } from '../detail/positions-detail.component';
import { PositionsUpdatePortalComponent } from '../update/positions-update.component';
import { PositionsRoutingResolveService } from './positions-routing-resolve.service';

const positionsRoute: Routes = [
  {
    path: '',
    component: PositionsPortalComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PositionsDetailPortalComponent,
    resolve: {
      positions: PositionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PositionsUpdatePortalComponent,
    resolve: {
      positions: PositionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PositionsUpdatePortalComponent,
    resolve: {
      positions: PositionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(positionsRoute)],
  exports: [RouterModule],
})
export class PositionsRoutingModule {}
