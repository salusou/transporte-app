import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParkingSectorSpaceComponent } from '../list/parking-sector-space.component';
import { ParkingSectorSpaceDetailComponent } from '../detail/parking-sector-space-detail.component';
import { ParkingSectorSpaceUpdateComponent } from '../update/parking-sector-space-update.component';
import { ParkingSectorSpaceRoutingResolveService } from './parking-sector-space-routing-resolve.service';

const parkingSectorSpaceRoute: Routes = [
  {
    path: '',
    component: ParkingSectorSpaceComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParkingSectorSpaceDetailComponent,
    resolve: {
      parkingSectorSpace: ParkingSectorSpaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParkingSectorSpaceUpdateComponent,
    resolve: {
      parkingSectorSpace: ParkingSectorSpaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParkingSectorSpaceUpdateComponent,
    resolve: {
      parkingSectorSpace: ParkingSectorSpaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(parkingSectorSpaceRoute)],
  exports: [RouterModule],
})
export class ParkingSectorSpaceRoutingModule {}
