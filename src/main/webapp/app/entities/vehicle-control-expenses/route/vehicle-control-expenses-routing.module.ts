import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleControlExpensesComponent } from '../list/vehicle-control-expenses.component';
import { VehicleControlExpensesDetailComponent } from '../detail/vehicle-control-expenses-detail.component';
import { VehicleControlExpensesUpdateComponent } from '../update/vehicle-control-expenses-update.component';
import { VehicleControlExpensesRoutingResolveService } from './vehicle-control-expenses-routing-resolve.service';

const vehicleControlExpensesRoute: Routes = [
  {
    path: '',
    component: VehicleControlExpensesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleControlExpensesDetailComponent,
    resolve: {
      vehicleControlExpenses: VehicleControlExpensesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleControlExpensesUpdateComponent,
    resolve: {
      vehicleControlExpenses: VehicleControlExpensesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleControlExpensesUpdateComponent,
    resolve: {
      vehicleControlExpenses: VehicleControlExpensesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleControlExpensesRoute)],
  exports: [RouterModule],
})
export class VehicleControlExpensesRoutingModule {}
