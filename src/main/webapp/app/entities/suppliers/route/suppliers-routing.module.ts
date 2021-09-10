import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SuppliersComponent } from '../list/suppliers.component';
import { SuppliersDetailComponent } from '../detail/suppliers-detail.component';
import { SuppliersUpdateComponent } from '../update/suppliers-update.component';
import { SuppliersRoutingResolveService } from './suppliers-routing-resolve.service';

const suppliersRoute: Routes = [
  {
    path: '',
    component: SuppliersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SuppliersDetailComponent,
    resolve: {
      suppliers: SuppliersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SuppliersUpdateComponent,
    resolve: {
      suppliers: SuppliersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SuppliersUpdateComponent,
    resolve: {
      suppliers: SuppliersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(suppliersRoute)],
  exports: [RouterModule],
})
export class SuppliersRoutingModule {}
