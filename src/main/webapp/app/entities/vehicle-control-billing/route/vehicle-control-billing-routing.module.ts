import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleControlBillingComponent } from '../list/vehicle-control-billing.component';
import { VehicleControlBillingDetailComponent } from '../detail/vehicle-control-billing-detail.component';
import { VehicleControlBillingUpdateComponent } from '../update/vehicle-control-billing-update.component';
import { VehicleControlBillingRoutingResolveService } from './vehicle-control-billing-routing-resolve.service';

const vehicleControlBillingRoute: Routes = [
  {
    path: '',
    component: VehicleControlBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleControlBillingDetailComponent,
    resolve: {
      vehicleControlBilling: VehicleControlBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleControlBillingUpdateComponent,
    resolve: {
      vehicleControlBilling: VehicleControlBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleControlBillingUpdateComponent,
    resolve: {
      vehicleControlBilling: VehicleControlBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleControlBillingRoute)],
  exports: [RouterModule],
})
export class VehicleControlBillingRoutingModule {}
