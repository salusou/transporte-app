import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleControlItem, VehicleControlItem } from '../vehicle-control-item.model';
import { VehicleControlItemService } from '../service/vehicle-control-item.service';

@Injectable({ providedIn: 'root' })
export class VehicleControlItemRoutingResolveService implements Resolve<IVehicleControlItem> {
  constructor(protected service: VehicleControlItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleControlItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleControlItem: HttpResponse<VehicleControlItem>) => {
          if (vehicleControlItem.body) {
            return of(vehicleControlItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleControlItem());
  }
}
