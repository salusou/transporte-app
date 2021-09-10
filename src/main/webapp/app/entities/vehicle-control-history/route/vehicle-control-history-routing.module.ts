import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleControlHistoryComponent } from '../list/vehicle-control-history.component';
import { VehicleControlHistoryDetailComponent } from '../detail/vehicle-control-history-detail.component';
import { VehicleControlHistoryUpdateComponent } from '../update/vehicle-control-history-update.component';
import { VehicleControlHistoryRoutingResolveService } from './vehicle-control-history-routing-resolve.service';

const vehicleControlHistoryRoute: Routes = [
  {
    path: '',
    component: VehicleControlHistoryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleControlHistoryDetailComponent,
    resolve: {
      vehicleControlHistory: VehicleControlHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleControlHistoryUpdateComponent,
    resolve: {
      vehicleControlHistory: VehicleControlHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleControlHistoryUpdateComponent,
    resolve: {
      vehicleControlHistory: VehicleControlHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleControlHistoryRoute)],
  exports: [RouterModule],
})
export class VehicleControlHistoryRoutingModule {}
