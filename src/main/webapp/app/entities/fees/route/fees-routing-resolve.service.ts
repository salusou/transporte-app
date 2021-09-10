import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFees, Fees } from '../fees.model';
import { FeesService } from '../service/fees.service';

@Injectable({ providedIn: 'root' })
export class FeesRoutingResolveService implements Resolve<IFees> {
  constructor(protected service: FeesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFees> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fees: HttpResponse<Fees>) => {
          if (fees.body) {
            return of(fees.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fees());
  }
}
