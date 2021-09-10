import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleControlExpenses, VehicleControlExpenses } from '../vehicle-control-expenses.model';
import { VehicleControlExpensesService } from '../service/vehicle-control-expenses.service';

@Injectable({ providedIn: 'root' })
export class VehicleControlExpensesRoutingResolveService implements Resolve<IVehicleControlExpenses> {
  constructor(protected service: VehicleControlExpensesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleControlExpenses> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleControlExpenses: HttpResponse<VehicleControlExpenses>) => {
          if (vehicleControlExpenses.body) {
            return of(vehicleControlExpenses.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleControlExpenses());
  }
}
