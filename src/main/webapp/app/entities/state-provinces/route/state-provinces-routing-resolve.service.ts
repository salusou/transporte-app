import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStateProvinces, StateProvinces } from '../state-provinces.model';
import { StateProvincesService } from '../service/state-provinces.service';

@Injectable({ providedIn: 'root' })
export class StateProvincesRoutingResolveService implements Resolve<IStateProvinces> {
  constructor(protected service: StateProvincesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStateProvinces> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stateProvinces: HttpResponse<StateProvinces>) => {
          if (stateProvinces.body) {
            return of(stateProvinces.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StateProvinces());
  }
}
