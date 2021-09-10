import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatusAttachments, StatusAttachments } from '../status-attachments.model';
import { StatusAttachmentsService } from '../service/status-attachments.service';

@Injectable({ providedIn: 'root' })
export class StatusAttachmentsRoutingResolveService implements Resolve<IStatusAttachments> {
  constructor(protected service: StatusAttachmentsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusAttachments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((statusAttachments: HttpResponse<StatusAttachments>) => {
          if (statusAttachments.body) {
            return of(statusAttachments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusAttachments());
  }
}
