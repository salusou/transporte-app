import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmployeeAttachmentsComponent } from '../list/employee-attachments.component';
import { EmployeeAttachmentsDetailComponent } from '../detail/employee-attachments-detail.component';
import { EmployeeAttachmentsUpdateComponent } from '../update/employee-attachments-update.component';
import { EmployeeAttachmentsRoutingResolveService } from './employee-attachments-routing-resolve.service';

const employeeAttachmentsRoute: Routes = [
  {
    path: '',
    component: EmployeeAttachmentsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeAttachmentsDetailComponent,
    resolve: {
      employeeAttachments: EmployeeAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeAttachmentsUpdateComponent,
    resolve: {
      employeeAttachments: EmployeeAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeAttachmentsUpdateComponent,
    resolve: {
      employeeAttachments: EmployeeAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(employeeAttachmentsRoute)],
  exports: [RouterModule],
})
export class EmployeeAttachmentsRoutingModule {}
