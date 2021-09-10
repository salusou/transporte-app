import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeesComponent } from '../list/fees.component';
import { FeesDetailComponent } from '../detail/fees-detail.component';
import { FeesUpdateComponent } from '../update/fees-update.component';
import { FeesRoutingResolveService } from './fees-routing-resolve.service';

const feesRoute: Routes = [
  {
    path: '',
    component: FeesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeesDetailComponent,
    resolve: {
      fees: FeesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeesUpdateComponent,
    resolve: {
      fees: FeesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeesUpdateComponent,
    resolve: {
      fees: FeesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(feesRoute)],
  exports: [RouterModule],
})
export class FeesRoutingModule {}
