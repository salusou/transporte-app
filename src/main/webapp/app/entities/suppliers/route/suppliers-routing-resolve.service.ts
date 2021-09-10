import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISuppliers, Suppliers } from '../suppliers.model';
import { SuppliersService } from '../service/suppliers.service';

@Injectable({ providedIn: 'root' })
export class SuppliersRoutingResolveService implements Resolve<ISuppliers> {
  constructor(protected service: SuppliersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISuppliers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((suppliers: HttpResponse<Suppliers>) => {
          if (suppliers.body) {
            return of(suppliers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Suppliers());
  }
}
