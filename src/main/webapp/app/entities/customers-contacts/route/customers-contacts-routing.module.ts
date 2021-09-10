import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomersContactsComponent } from '../list/customers-contacts.component';
import { CustomersContactsDetailComponent } from '../detail/customers-contacts-detail.component';
import { CustomersContactsUpdateComponent } from '../update/customers-contacts-update.component';
import { CustomersContactsRoutingResolveService } from './customers-contacts-routing-resolve.service';

const customersContactsRoute: Routes = [
  {
    path: '',
    component: CustomersContactsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomersContactsDetailComponent,
    resolve: {
      customersContacts: CustomersContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomersContactsUpdateComponent,
    resolve: {
      customersContacts: CustomersContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomersContactsUpdateComponent,
    resolve: {
      customersContacts: CustomersContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customersContactsRoute)],
  exports: [RouterModule],
})
export class CustomersContactsRoutingModule {}
