import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomerAttachments, CustomerAttachments } from '../customer-attachments.model';
import { CustomerAttachmentsService } from '../service/customer-attachments.service';

@Injectable({ providedIn: 'root' })
export class CustomerAttachmentsRoutingResolveService implements Resolve<ICustomerAttachments> {
  constructor(protected service: CustomerAttachmentsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerAttachments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customerAttachments: HttpResponse<CustomerAttachments>) => {
          if (customerAttachments.body) {
            return of(customerAttachments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerAttachments());
  }
}
