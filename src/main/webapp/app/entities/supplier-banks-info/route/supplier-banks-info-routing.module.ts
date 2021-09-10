import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SupplierBanksInfoComponent } from '../list/supplier-banks-info.component';
import { SupplierBanksInfoDetailComponent } from '../detail/supplier-banks-info-detail.component';
import { SupplierBanksInfoUpdateComponent } from '../update/supplier-banks-info-update.component';
import { SupplierBanksInfoRoutingResolveService } from './supplier-banks-info-routing-resolve.service';

const supplierBanksInfoRoute: Routes = [
  {
    path: '',
    component: SupplierBanksInfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SupplierBanksInfoDetailComponent,
    resolve: {
      supplierBanksInfo: SupplierBanksInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SupplierBanksInfoUpdateComponent,
    resolve: {
      supplierBanksInfo: SupplierBanksInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SupplierBanksInfoUpdateComponent,
    resolve: {
      supplierBanksInfo: SupplierBanksInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(supplierBanksInfoRoute)],
  exports: [RouterModule],
})
export class SupplierBanksInfoRoutingModule {}
