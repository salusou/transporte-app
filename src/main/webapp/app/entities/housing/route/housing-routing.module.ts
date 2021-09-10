import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HousingComponent } from '../list/housing.component';
import { HousingDetailComponent } from '../detail/housing-detail.component';
import { HousingUpdateComponent } from '../update/housing-update.component';
import { HousingRoutingResolveService } from './housing-routing-resolve.service';

const housingRoute: Routes = [
  {
    path: '',
    component: HousingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HousingDetailComponent,
    resolve: {
      housing: HousingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HousingUpdateComponent,
    resolve: {
      housing: HousingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HousingUpdateComponent,
    resolve: {
      housing: HousingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(housingRoute)],
  exports: [RouterModule],
})
export class HousingRoutingModule {}
