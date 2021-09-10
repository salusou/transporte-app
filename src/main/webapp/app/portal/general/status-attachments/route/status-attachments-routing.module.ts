import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatusAttachmentsPortalComponent } from '../list/status-attachments.component';
import { StatusAttachmentsDetailPortalComponent } from '../detail/status-attachments-detail.component';
import { StatusAttachmentsUpdatePortalComponent } from '../update/status-attachments-update.component';
import { StatusAttachmentsRoutingResolveService } from './status-attachments-routing-resolve.service';

const statusAttachmentsRoute: Routes = [
  {
    path: '',
    component: StatusAttachmentsPortalComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatusAttachmentsDetailPortalComponent,
    resolve: {
      statusAttachments: StatusAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatusAttachmentsUpdatePortalComponent,
    resolve: {
      statusAttachments: StatusAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatusAttachmentsUpdatePortalComponent,
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
