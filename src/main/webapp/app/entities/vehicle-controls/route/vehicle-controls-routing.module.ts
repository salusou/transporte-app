import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleControlsComponent } from '../list/vehicle-controls.component';
import { VehicleControlsDetailComponent } from '../detail/vehicle-controls-detail.component';
import { VehicleControlsUpdateComponent } from '../update/vehicle-controls-update.component';
import { VehicleControlsRoutingResolveService } from './vehicle-controls-routing-resolve.service';

const vehicleControlsRoute: Routes = [
  {
    path: '',
    component: VehicleControlsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleControlsDetailComponent,
    resolve: {
      vehicleControls: VehicleControlsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleControlsUpdateComponent,
    resolve: {
      vehicleControls: VehicleControlsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleControlsUpdateComponent,
    resolve: {
      vehicleControls: VehicleControlsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleControlsRoute)],
  exports: [RouterModule],
})
export class VehicleControlsRoutingModule {}
