import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmployeeComponentsDataComponent } from '../list/employee-components-data.component';
import { EmployeeComponentsDataDetailComponent } from '../detail/employee-components-data-detail.component';
import { EmployeeComponentsDataUpdateComponent } from '../update/employee-components-data-update.component';
import { EmployeeComponentsDataRoutingResolveService } from './employee-components-data-routing-resolve.service';

const employeeComponentsDataRoute: Routes = [
  {
    path: '',
    component: EmployeeComponentsDataComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeComponentsDataDetailComponent,
    resolve: {
      employeeComponentsData: EmployeeComponentsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeComponentsDataUpdateComponent,
    resolve: {
      employeeComponentsData: EmployeeComponentsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeComponentsDataUpdateComponent,
    resolve: {
      employeeComponentsData: EmployeeComponentsDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(employeeComponentsDataRoute)],
  exports: [RouterModule],
})
export class EmployeeComponentsDataRoutingModule {}
