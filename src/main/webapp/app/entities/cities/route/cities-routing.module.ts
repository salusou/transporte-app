import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CitiesComponent } from '../list/cities.component';
import { CitiesDetailComponent } from '../detail/cities-detail.component';
import { CitiesUpdateComponent } from '../update/cities-update.component';
import { CitiesRoutingResolveService } from './cities-routing-resolve.service';

const citiesRoute: Routes = [
  {
    path: '',
    component: CitiesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CitiesDetailComponent,
    resolve: {
      cities: CitiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CitiesUpdateComponent,
    resolve: {
      cities: CitiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CitiesUpdateComponent,
    resolve: {
      cities: CitiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(citiesRoute)],
  exports: [RouterModule],
})
export class CitiesRoutingModule {}
