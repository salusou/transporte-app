import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployeeAttachments, EmployeeAttachments } from '../employee-attachments.model';
import { EmployeeAttachmentsService } from '../service/employee-attachments.service';

@Injectable({ providedIn: 'root' })
export class EmployeeAttachmentsRoutingResolveService implements Resolve<IEmployeeAttachments> {
  constructor(protected service: EmployeeAttachmentsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeAttachments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((employeeAttachments: HttpResponse<EmployeeAttachments>) => {
          if (employeeAttachments.body) {
            return of(employeeAttachments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeAttachments());
  }
}
