import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StateProvincesComponent } from '../list/state-provinces.component';
import { StateProvincesDetailComponent } from '../detail/state-provinces-detail.component';
import { StateProvincesUpdateComponent } from '../update/state-provinces-update.component';
import { StateProvincesRoutingResolveService } from './state-provinces-routing-resolve.service';

const stateProvincesRoute: Routes = [
  {
    path: '',
    component: StateProvincesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StateProvincesDetailComponent,
    resolve: {
      stateProvinces: StateProvincesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StateProvincesUpdateComponent,
    resolve: {
      stateProvinces: StateProvincesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StateProvincesUpdateComponent,
    resolve: {
      stateProvinces: StateProvincesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stateProvincesRoute)],
  exports: [RouterModule],
})
export class StateProvincesRoutingModule {}
