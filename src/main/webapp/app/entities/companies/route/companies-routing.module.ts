import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompaniesComponent } from '../list/companies.component';
import { CompaniesDetailComponent } from '../detail/companies-detail.component';
import { CompaniesUpdateComponent } from '../update/companies-update.component';
import { CompaniesRoutingResolveService } from './companies-routing-resolve.service';

const companiesRoute: Routes = [
  {
    path: '',
    component: CompaniesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompaniesDetailComponent,
    resolve: {
      companies: CompaniesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompaniesUpdateComponent,
    resolve: {
      companies: CompaniesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompaniesUpdateComponent,
    resolve: {
      companies: CompaniesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companiesRoute)],
  exports: [RouterModule],
})
export class CompaniesRoutingModule {}
