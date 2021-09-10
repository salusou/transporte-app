import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AffiliatesComponent } from '../list/affiliates.component';
import { AffiliatesDetailComponent } from '../detail/affiliates-detail.component';
import { AffiliatesUpdateComponent } from '../update/affiliates-update.component';
import { AffiliatesRoutingResolveService } from './affiliates-routing-resolve.service';

const affiliatesRoute: Routes = [
  {
    path: '',
    component: AffiliatesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AffiliatesDetailComponent,
    resolve: {
      affiliates: AffiliatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AffiliatesUpdateComponent,
    resolve: {
      affiliates: AffiliatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AffiliatesUpdateComponent,
    resolve: {
      affiliates: AffiliatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(affiliatesRoute)],
  exports: [RouterModule],
})
export class AffiliatesRoutingModule {}
