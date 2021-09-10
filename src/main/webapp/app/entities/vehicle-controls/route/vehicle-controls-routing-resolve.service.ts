import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleControls, VehicleControls } from '../vehicle-controls.model';
import { VehicleControlsService } from '../service/vehicle-controls.service';

@Injectable({ providedIn: 'root' })
export class VehicleControlsRoutingResolveService implements Resolve<IVehicleControls> {
  constructor(protected service: VehicleControlsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleControls> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleControls: HttpResponse<VehicleControls>) => {
          if (vehicleControls.body) {
            return of(vehicleControls.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleControls());
  }
}
