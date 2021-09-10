import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParkingSectorComponent } from '../list/parking-sector.component';
import { ParkingSectorDetailComponent } from '../detail/parking-sector-detail.component';
import { ParkingSectorUpdateComponent } from '../update/parking-sector-update.component';
import { ParkingSectorRoutingResolveService } from './parking-sector-routing-resolve.service';

const parkingSectorRoute: Routes = [
  {
    path: '',
    component: ParkingSectorComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParkingSectorDetailComponent,
    resolve: {
      parkingSector: ParkingSectorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParkingSectorUpdateComponent,
    resolve: {
      parkingSector: ParkingSectorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParkingSectorUpdateComponent,
    resolve: {
      parkingSector: ParkingSectorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(parkingSectorRoute)],
  exports: [RouterModule],
})
export class ParkingSectorRoutingModule {}
