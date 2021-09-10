import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleLocationStatusComponent } from '../list/vehicle-location-status.component';
import { VehicleLocationStatusDetailComponent } from '../detail/vehicle-location-status-detail.component';
import { VehicleLocationStatusUpdateComponent } from '../update/vehicle-location-status-update.component';
import { VehicleLocationStatusRoutingResolveService } from './vehicle-location-status-routing-resolve.service';

const vehicleLocationStatusRoute: Routes = [
  {
    path: '',
    component: VehicleLocationStatusComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleLocationStatusDetailComponent,
    resolve: {
      vehicleLocationStatus: VehicleLocationStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleLocationStatusUpdateComponent,
    resolve: {
      vehicleLocationStatus: VehicleLocationStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleLocationStatusUpdateComponent,
    resolve: {
      vehicleLocationStatus: VehicleLocationStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleLocationStatusRoute)],
  exports: [RouterModule],
})
export class VehicleLocationStatusRoutingModule {}
