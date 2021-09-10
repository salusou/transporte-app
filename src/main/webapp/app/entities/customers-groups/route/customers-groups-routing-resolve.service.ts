import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomersGroups, CustomersGroups } from '../customers-groups.model';
import { CustomersGroupsService } from '../service/customers-groups.service';

@Injectable({ providedIn: 'root' })
export class CustomersGroupsRoutingResolveService implements Resolve<ICustomersGroups> {
  constructor(protected service: CustomersGroupsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomersGroups> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customersGroups: HttpResponse<CustomersGroups>) => {
          if (customersGroups.body) {
            return of(customersGroups.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomersGroups());
  }
}
