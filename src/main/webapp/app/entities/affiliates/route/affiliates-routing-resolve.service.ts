import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAffiliates, Affiliates } from '../affiliates.model';
import { AffiliatesService } from '../service/affiliates.service';

@Injectable({ providedIn: 'root' })
export class AffiliatesRoutingResolveService implements Resolve<IAffiliates> {
  constructor(protected service: AffiliatesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAffiliates> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((affiliates: HttpResponse<Affiliates>) => {
          if (affiliates.body) {
            return of(affiliates.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Affiliates());
  }
}
