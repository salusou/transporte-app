import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdministrativeFeesRangesComponent } from '../list/administrative-fees-ranges.component';
import { AdministrativeFeesRangesDetailComponent } from '../detail/administrative-fees-ranges-detail.component';
import { AdministrativeFeesRangesUpdateComponent } from '../update/administrative-fees-ranges-update.component';
import { AdministrativeFeesRangesRoutingResolveService } from './administrative-fees-ranges-routing-resolve.service';

const administrativeFeesRangesRoute: Routes = [
  {
    path: '',
    component: AdministrativeFeesRangesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministrativeFeesRangesDetailComponent,
    resolve: {
      administrativeFeesRanges: AdministrativeFeesRangesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministrativeFeesRangesUpdateComponent,
    resolve: {
      administrativeFeesRanges: AdministrativeFeesRangesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdministrativeFeesRangesUpdateComponent,
    resolve: {
      administrativeFeesRanges: AdministrativeFeesRangesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(administrativeFeesRangesRoute)],
  exports: [RouterModule],
})
export class AdministrativeFeesRangesRoutingModule {}
