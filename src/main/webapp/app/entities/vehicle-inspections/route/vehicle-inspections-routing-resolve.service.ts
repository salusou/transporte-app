import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleInspections, VehicleInspections } from '../vehicle-inspections.model';
import { VehicleInspectionsService } from '../service/vehicle-inspections.service';

@Injectable({ providedIn: 'root' })
export class VehicleInspectionsRoutingResolveService implements Resolve<IVehicleInspections> {
  constructor(protected service: VehicleInspectionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleInspections> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleInspections: HttpResponse<VehicleInspections>) => {
          if (vehicleInspections.body) {
            return of(vehicleInspections.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleInspections());
  }
}
