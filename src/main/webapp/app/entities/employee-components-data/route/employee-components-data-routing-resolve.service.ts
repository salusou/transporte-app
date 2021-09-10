import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployeeComponentsData, EmployeeComponentsData } from '../employee-components-data.model';
import { EmployeeComponentsDataService } from '../service/employee-components-data.service';

@Injectable({ providedIn: 'root' })
export class EmployeeComponentsDataRoutingResolveService implements Resolve<IEmployeeComponentsData> {
  constructor(protected service: EmployeeComponentsDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeComponentsData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((employeeComponentsData: HttpResponse<EmployeeComponentsData>) => {
          if (employeeComponentsData.body) {
            return of(employeeComponentsData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeComponentsData());
  }
}
