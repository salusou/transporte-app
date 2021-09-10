import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusAttachmentsComponent } from '../list/status-attachments.component';
import { StatusAttachmentsDetailComponent } from '../detail/status-attachments-detail.component';
import { StatusAttachmentsUpdateComponent } from '../update/status-attachments-update.component';
import { StatusAttachmentsRoutingResolveService } from './status-attachments-routing-resolve.service';

const statusAttachmentsRoute: Routes = [
  {
    path: '',
    component: StatusAttachmentsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusAttachmentsDetailComponent,
    resolve: {
      statusAttachments: StatusAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusAttachmentsUpdateComponent,
    resolve: {
      statusAttachments: StatusAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusAttachmentsUpdateComponent,
    resolve: {
      statusAttachments: StatusAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statusAttachmentsRoute)],
  exports: [RouterModule],
})
export class StatusAttachmentsRoutingModule {}
