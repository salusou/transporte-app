import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHousingVehicleItem, HousingVehicleItem } from '../housing-vehicle-item.model';
import { HousingVehicleItemService } from '../service/housing-vehicle-item.service';

@Injectable({ providedIn: 'root' })
export class HousingVehicleItemRoutingResolveService implements Resolve<IHousingVehicleItem> {
  constructor(protected service: HousingVehicleItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHousingVehicleItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((housingVehicleItem: HttpResponse<HousingVehicleItem>) => {
          if (housingVehicleItem.body) {
            return of(housingVehicleItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HousingVehicleItem());
  }
}
