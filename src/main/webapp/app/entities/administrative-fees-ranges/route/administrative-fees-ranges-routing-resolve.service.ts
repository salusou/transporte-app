import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdministrativeFeesRanges, AdministrativeFeesRanges } from '../administrative-fees-ranges.model';
import { AdministrativeFeesRangesService } from '../service/administrative-fees-ranges.service';

@Injectable({ providedIn: 'root' })
export class AdministrativeFeesRangesRoutingResolveService implements Resolve<IAdministrativeFeesRanges> {
  constructor(protected service: AdministrativeFeesRangesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdministrativeFeesRanges> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((administrativeFeesRanges: HttpResponse<AdministrativeFeesRanges>) => {
          if (administrativeFeesRanges.body) {
            return of(administrativeFeesRanges.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdministrativeFeesRanges());
  }
}
