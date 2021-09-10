import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomersContacts, CustomersContacts } from '../customers-contacts.model';
import { CustomersContactsService } from '../service/customers-contacts.service';

@Injectable({ providedIn: 'root' })
export class CustomersContactsRoutingResolveService implements Resolve<ICustomersContacts> {
  constructor(protected service: CustomersContactsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomersContacts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customersContacts: HttpResponse<CustomersContacts>) => {
          if (customersContacts.body) {
            return of(customersContacts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomersContacts());
  }
}
