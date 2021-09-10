import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParkingSectorSpace, ParkingSectorSpace } from '../parking-sector-space.model';
import { ParkingSectorSpaceService } from '../service/parking-sector-space.service';

@Injectable({ providedIn: 'root' })
export class ParkingSectorSpaceRoutingResolveService implements Resolve<IParkingSectorSpace> {
  constructor(protected service: ParkingSectorSpaceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParkingSectorSpace> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((parkingSectorSpace: HttpResponse<ParkingSectorSpace>) => {
          if (parkingSectorSpace.body) {
            return of(parkingSectorSpace.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ParkingSectorSpace());
  }
}
