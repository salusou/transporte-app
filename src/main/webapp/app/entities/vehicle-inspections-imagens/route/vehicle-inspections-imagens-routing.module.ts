import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleInspectionsImagensComponent } from '../list/vehicle-inspections-imagens.component';
import { VehicleInspectionsImagensDetailComponent } from '../detail/vehicle-inspections-imagens-detail.component';
import { VehicleInspectionsImagensUpdateComponent } from '../update/vehicle-inspections-imagens-update.component';
import { VehicleInspectionsImagensRoutingResolveService } from './vehicle-inspections-imagens-routing-resolve.service';

const vehicleInspectionsImagensRoute: Routes = [
  {
    path: '',
    component: VehicleInspectionsImagensComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleInspectionsImagensDetailComponent,
    resolve: {
      vehicleInspectionsImagens: VehicleInspectionsImagensRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleInspectionsImagensUpdateComponent,
    resolve: {
      vehicleInspectionsImagens: VehicleInspectionsImagensRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleInspectionsImagensUpdateComponent,
    resolve: {
      vehicleInspectionsImagens: VehicleInspectionsImagensRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleInspectionsImagensRoute)],
  exports: [RouterModule],
})
export class VehicleInspectionsImagensRoutingModule {}
