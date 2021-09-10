import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SupplierContactsComponent } from '../list/supplier-contacts.component';
import { SupplierContactsDetailComponent } from '../detail/supplier-contacts-detail.component';
import { SupplierContactsUpdateComponent } from '../update/supplier-contacts-update.component';
import { SupplierContactsRoutingResolveService } from './supplier-contacts-routing-resolve.service';

const supplierContactsRoute: Routes = [
  {
    path: '',
    component: SupplierContactsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SupplierContactsDetailComponent,
    resolve: {
      supplierContacts: SupplierContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SupplierContactsUpdateComponent,
    resolve: {
      supplierContacts: SupplierContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SupplierContactsUpdateComponent,
    resolve: {
      supplierContacts: SupplierContactsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(supplierContactsRoute)],
  exports: [RouterModule],
})
export class SupplierContactsRoutingModule {}
