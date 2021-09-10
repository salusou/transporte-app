import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleInspectionsImagens, VehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';
import { VehicleInspectionsImagensService } from '../service/vehicle-inspections-imagens.service';

@Injectable({ providedIn: 'root' })
export class VehicleInspectionsImagensRoutingResolveService implements Resolve<IVehicleInspectionsImagens> {
  constructor(protected service: VehicleInspectionsImagensService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleInspectionsImagens> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleInspectionsImagens: HttpResponse<VehicleInspectionsImagens>) => {
          if (vehicleInspectionsImagens.body) {
            return of(vehicleInspectionsImagens.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleInspectionsImagens());
  }
}
