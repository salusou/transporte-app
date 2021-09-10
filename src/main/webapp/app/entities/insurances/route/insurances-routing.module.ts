import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InsurancesComponent } from '../list/insurances.component';
import { InsurancesDetailComponent } from '../detail/insurances-detail.component';
import { InsurancesUpdateComponent } from '../update/insurances-update.component';
import { InsurancesRoutingResolveService } from './insurances-routing-resolve.service';

const insurancesRoute: Routes = [
  {
    path: '',
    component: InsurancesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InsurancesDetailComponent,
    resolve: {
      insurances: InsurancesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InsurancesUpdateComponent,
    resolve: {
      insurances: InsurancesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InsurancesUpdateComponent,
    resolve: {
      insurances: InsurancesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(insurancesRoute)],
  exports: [RouterModule],
})
export class InsurancesRoutingModule {}
