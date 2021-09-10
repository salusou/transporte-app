import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleLocationStatus, VehicleLocationStatus } from '../vehicle-location-status.model';
import { VehicleLocationStatusService } from '../service/vehicle-location-status.service';

@Injectable({ providedIn: 'root' })
export class VehicleLocationStatusRoutingResolveService implements Resolve<IVehicleLocationStatus> {
  constructor(protected service: VehicleLocationStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleLocationStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleLocationStatus: HttpResponse<VehicleLocationStatus>) => {
          if (vehicleLocationStatus.body) {
            return of(vehicleLocationStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleLocationStatus());
  }
}
