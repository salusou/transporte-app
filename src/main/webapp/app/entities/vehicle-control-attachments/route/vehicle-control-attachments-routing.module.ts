import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleControlAttachmentsComponent } from '../list/vehicle-control-attachments.component';
import { VehicleControlAttachmentsDetailComponent } from '../detail/vehicle-control-attachments-detail.component';
import { VehicleControlAttachmentsUpdateComponent } from '../update/vehicle-control-attachments-update.component';
import { VehicleControlAttachmentsRoutingResolveService } from './vehicle-control-attachments-routing-resolve.service';

const vehicleControlAttachmentsRoute: Routes = [
  {
    path: '',
    component: VehicleControlAttachmentsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleControlAttachmentsDetailComponent,
    resolve: {
      vehicleControlAttachments: VehicleControlAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleControlAttachmentsUpdateComponent,
    resolve: {
      vehicleControlAttachments: VehicleControlAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleControlAttachmentsUpdateComponent,
    resolve: {
      vehicleControlAttachments: VehicleControlAttachmentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleControlAttachmentsRoute)],
  exports: [RouterModule],
})
export class VehicleControlAttachmentsRoutingModule {}
