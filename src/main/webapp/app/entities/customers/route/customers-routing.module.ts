import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomersComponent } from '../list/customers.component';
import { CustomersDetailComponent } from '../detail/customers-detail.component';
import { CustomersUpdateComponent } from '../update/customers-update.component';
import { CustomersRoutingResolveService } from './customers-routing-resolve.service';

const customersRoute: Routes = [
  {
    path: '',
    component: CustomersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomersDetailComponent,
    resolve: {
      customers: CustomersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomersUpdateComponent,
    resolve: {
      customers: CustomersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomersUpdateComponent,
    resolve: {
      customers: CustomersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customersRoute)],
  exports: [RouterModule],
})
export class CustomersRoutingModule {}
