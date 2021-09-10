import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleControlBilling, VehicleControlBilling } from '../vehicle-control-billing.model';
import { VehicleControlBillingService } from '../service/vehicle-control-billing.service';

@Injectable({ providedIn: 'root' })
export class VehicleControlBillingRoutingResolveService implements Resolve<IVehicleControlBilling> {
  constructor(protected service: VehicleControlBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleControlBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleControlBilling: HttpResponse<VehicleControlBilling>) => {
          if (vehicleControlBilling.body) {
            return of(vehicleControlBilling.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleControlBilling());
  }
}
