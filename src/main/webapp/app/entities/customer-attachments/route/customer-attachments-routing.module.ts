import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerAttachmentsComponent } from '../list/customer-attachments.component';
import { CustomerAttachmentsDetailComponent } from '../detail/customer-attachments-detail.component';
import { CustomerAttachmentsUpdateComponent } from '../update/customer-attachments-update.component';
import { CustomerAttachmentsRoutingResolveService } from './customer-attachments-routing-resolve.service';

const customerAttachmentsRoute: Routes = [
  {
    path: '',
    component: CustomerAttachmentsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerAttachmentsDetailComponent,
    resolve: {
      customerAttachments: CustomerAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerAttachmentsUpdateComponent,
    resolve: {
      customerAttachments: CustomerAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerAttachmentsUpdateComponent,
    resolve: {
      customerAttachments: CustomerAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customerAttachmentsRoute)],
  exports: [RouterModule],
})
export class CustomerAttachmentsRoutingModule {}
