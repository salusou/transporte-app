import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISupplierContacts, SupplierContacts } from '../supplier-contacts.model';
import { SupplierContactsService } from '../service/supplier-contacts.service';

@Injectable({ providedIn: 'root' })
export class SupplierContactsRoutingResolveService implements Resolve<ISupplierContacts> {
  constructor(protected service: SupplierContactsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupplierContacts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((supplierContacts: HttpResponse<SupplierContacts>) => {
          if (supplierContacts.body) {
            return of(supplierContacts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SupplierContacts());
  }
}
