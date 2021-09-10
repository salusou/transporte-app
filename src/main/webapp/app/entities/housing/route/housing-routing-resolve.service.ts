import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHousing, Housing } from '../housing.model';
import { HousingService } from '../service/housing.service';

@Injectable({ providedIn: 'root' })
export class HousingRoutingResolveService implements Resolve<IHousing> {
  constructor(protected service: HousingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHousing> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((housing: HttpResponse<Housing>) => {
          if (housing.body) {
            return of(housing.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Housing());
  }
}
