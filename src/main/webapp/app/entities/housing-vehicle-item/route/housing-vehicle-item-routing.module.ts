import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HousingVehicleItemComponent } from '../list/housing-vehicle-item.component';
import { HousingVehicleItemDetailComponent } from '../detail/housing-vehicle-item-detail.component';
import { HousingVehicleItemUpdateComponent } from '../update/housing-vehicle-item-update.component';
import { HousingVehicleItemRoutingResolveService } from './housing-vehicle-item-routing-resolve.service';

const housingVehicleItemRoute: Routes = [
  {
    path: '',
    component: HousingVehicleItemComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HousingVehicleItemDetailComponent,
    resolve: {
      housingVehicleItem: HousingVehicleItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HousingVehicleItemUpdateComponent,
    resolve: {
      housingVehicleItem: HousingVehicleItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HousingVehicleItemUpdateComponent,
    resolve: {
      housingVehicleItem: HousingVehicleItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(housingVehicleItemRoute)],
  exports: [RouterModule],
})
export class HousingVehicleItemRoutingModule {}
