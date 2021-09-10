import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISupplierBanksInfo, SupplierBanksInfo } from '../supplier-banks-info.model';
import { SupplierBanksInfoService } from '../service/supplier-banks-info.service';

@Injectable({ providedIn: 'root' })
export class SupplierBanksInfoRoutingResolveService implements Resolve<ISupplierBanksInfo> {
  constructor(protected service: SupplierBanksInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupplierBanksInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((supplierBanksInfo: HttpResponse<SupplierBanksInfo>) => {
          if (supplierBanksInfo.body) {
            return of(supplierBanksInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SupplierBanksInfo());
  }
}
