import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomersGroupsComponent } from '../list/customers-groups.component';
import { CustomersGroupsDetailComponent } from '../detail/customers-groups-detail.component';
import { CustomersGroupsUpdateComponent } from '../update/customers-groups-update.component';
import { CustomersGroupsRoutingResolveService } from './customers-groups-routing-resolve.service';

const customersGroupsRoute: Routes = [
  {
    path: '',
    component: CustomersGroupsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomersGroupsDetailComponent,
    resolve: {
      customersGroups: CustomersGroupsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomersGroupsUpdateComponent,
    resolve: {
      customersGroups: CustomersGroupsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomersGroupsUpdateComponent,
    resolve: {
      customersGroups: CustomersGroupsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customersGroupsRoute)],
  exports: [RouterModule],
})
export class CustomersGroupsRoutingModule {}
