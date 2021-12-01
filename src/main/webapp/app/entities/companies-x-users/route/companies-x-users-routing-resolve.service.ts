import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompaniesXUsers, CompaniesXUsers } from '../companies-x-users.model';
import { CompaniesXUsersService } from '../service/companies-x-users.service';

@Injectable({ providedIn: 'root' })
export class CompaniesXUsersRoutingResolveService implements Resolve<ICompaniesXUsers> {
  constructor(protected service: CompaniesXUsersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompaniesXUsers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companiesXUsers: HttpResponse<CompaniesXUsers>) => {
          if (companiesXUsers.body) {
            return of(companiesXUsers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompaniesXUsers());
  }
}
