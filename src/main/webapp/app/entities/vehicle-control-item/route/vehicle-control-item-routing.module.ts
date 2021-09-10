import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleControlItemComponent } from '../list/vehicle-control-item.component';
import { VehicleControlItemDetailComponent } from '../detail/vehicle-control-item-detail.component';
import { VehicleControlItemUpdateComponent } from '../update/vehicle-control-item-update.component';
import { VehicleControlItemRoutingResolveService } from './vehicle-control-item-routing-resolve.service';

const vehicleControlItemRoute: Routes = [
  {
    path: '',
    component: VehicleControlItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleControlItemDetailComponent,
    resolve: {
      vehicleControlItem: VehicleControlItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleControlItemUpdateComponent,
    resolve: {
      vehicleControlItem: VehicleControlItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleControlItemUpdateComponent,
    resolve: {
      vehicleControlItem: VehicleControlItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleControlItemRoute)],
  exports: [RouterModule],
})
export class VehicleControlItemRoutingModule {}
