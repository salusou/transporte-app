import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInsurances, Insurances } from '../insurances.model';
import { InsurancesService } from '../service/insurances.service';

@Injectable({ providedIn: 'root' })
export class InsurancesRoutingResolveService implements Resolve<IInsurances> {
  constructor(protected service: InsurancesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInsurances> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((insurances: HttpResponse<Insurances>) => {
          if (insurances.body) {
            return of(insurances.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Insurances());
  }
}
