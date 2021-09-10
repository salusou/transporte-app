import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleControlHistory, VehicleControlHistory } from '../vehicle-control-history.model';
import { VehicleControlHistoryService } from '../service/vehicle-control-history.service';

@Injectable({ providedIn: 'root' })
export class VehicleControlHistoryRoutingResolveService implements Resolve<IVehicleControlHistory> {
  constructor(protected service: VehicleControlHistoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleControlHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleControlHistory: HttpResponse<VehicleControlHistory>) => {
          if (vehicleControlHistory.body) {
            return of(vehicleControlHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleControlHistory());
  }
}
