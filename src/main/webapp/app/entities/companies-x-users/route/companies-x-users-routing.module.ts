import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompaniesXUsersComponent } from '../list/companies-x-users.component';
import { CompaniesXUsersDetailComponent } from '../detail/companies-x-users-detail.component';
import { CompaniesXUsersUpdateComponent } from '../update/companies-x-users-update.component';
import { CompaniesXUsersRoutingResolveService } from './companies-x-users-routing-resolve.service';

const companiesXUsersRoute: Routes = [
  {
    path: '',
    component: CompaniesXUsersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompaniesXUsersDetailComponent,
    resolve: {
      companiesXUsers: CompaniesXUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompaniesXUsersUpdateComponent,
    resolve: {
      companiesXUsers: CompaniesXUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompaniesXUsersUpdateComponent,
    resolve: {
      companiesXUsers: CompaniesXUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companiesXUsersRoute)],
  exports: [RouterModule],
})
export class CompaniesXUsersRoutingModule {}
