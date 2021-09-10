import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParkingSector, ParkingSector } from '../parking-sector.model';
import { ParkingSectorService } from '../service/parking-sector.service';

@Injectable({ providedIn: 'root' })
export class ParkingSectorRoutingResolveService implements Resolve<IParkingSector> {
  constructor(protected service: ParkingSectorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParkingSector> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((parkingSector: HttpResponse<ParkingSector>) => {
          if (parkingSector.body) {
            return of(parkingSector.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ParkingSector());
  }
}
