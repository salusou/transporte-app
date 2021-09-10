import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleInspectionsComponent } from '../list/vehicle-inspections.component';
import { VehicleInspectionsDetailComponent } from '../detail/vehicle-inspections-detail.component';
import { VehicleInspectionsUpdateComponent } from '../update/vehicle-inspections-update.component';
import { VehicleInspectionsRoutingResolveService } from './vehicle-inspections-routing-resolve.service';

const vehicleInspectionsRoute: Routes = [
  {
    path: '',
    component: VehicleInspectionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleInspectionsDetailComponent,
    resolve: {
      vehicleInspections: VehicleInspectionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleInspectionsUpdateComponent,
    resolve: {
      vehicleInspections: VehicleInspectionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleInspectionsUpdateComponent,
    resolve: {
      vehicleInspections: VehicleInspectionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleInspectionsRoute)],
  exports: [RouterModule],
})
export class VehicleInspectionsRoutingModule {}
