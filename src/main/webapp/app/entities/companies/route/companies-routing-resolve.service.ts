import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanies, Companies } from '../companies.model';
import { CompaniesService } from '../service/companies.service';

@Injectable({ providedIn: 'root' })
export class CompaniesRoutingResolveService implements Resolve<ICompanies> {
  constructor(protected service: CompaniesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanies> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companies: HttpResponse<Companies>) => {
          if (companies.body) {
            return of(companies.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Companies());
  }
}
