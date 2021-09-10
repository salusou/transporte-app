import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleControlAttachments, VehicleControlAttachments } from '../vehicle-control-attachments.model';
import { VehicleControlAttachmentsService } from '../service/vehicle-control-attachments.service';

@Injectable({ providedIn: 'root' })
export class VehicleControlAttachmentsRoutingResolveService implements Resolve<IVehicleControlAttachments> {
  constructor(protected service: VehicleControlAttachmentsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleControlAttachments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleControlAttachments: HttpResponse<VehicleControlAttachments>) => {
          if (vehicleControlAttachments.body) {
            return of(vehicleControlAttachments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleControlAttachments());
  }
}
