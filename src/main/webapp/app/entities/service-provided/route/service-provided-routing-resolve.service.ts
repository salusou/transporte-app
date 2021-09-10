import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServiceProvided, ServiceProvided } from '../service-provided.model';
import { ServiceProvidedService } from '../service/service-provided.service';

@Injectable({ providedIn: 'root' })
export class ServiceProvidedRoutingResolveService implements Resolve<IServiceProvided> {
  constructor(protected service: ServiceProvidedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceProvided> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((serviceProvided: HttpResponse<ServiceProvided>) => {
          if (serviceProvided.body) {
            return of(serviceProvided.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceProvided());
  }
}
