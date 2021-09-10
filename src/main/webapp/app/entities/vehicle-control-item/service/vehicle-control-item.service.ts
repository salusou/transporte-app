import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleControlItem, getVehicleControlItemIdentifier } from '../vehicle-control-item.model';

export type EntityResponseType = HttpResponse<IVehicleControlItem>;
export type EntityArrayResponseType = HttpResponse<IVehicleControlItem[]>;

@Injectable({ providedIn: 'root' })
export class VehicleControlItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-control-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleControlItem: IVehicleControlItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlItem);
    return this.http
      .post<IVehicleControlItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleControlItem: IVehicleControlItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlItem);
    return this.http
      .put<IVehicleControlItem>(`${this.resourceUrl}/${getVehicleControlItemIdentifier(vehicleControlItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleControlItem: IVehicleControlItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlItem);
    return this.http
      .patch<IVehicleControlItem>(`${this.resourceUrl}/${getVehicleControlItemIdentifier(vehicleControlItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleControlItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleControlItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleControlItemToCollectionIfMissing(
    vehicleControlItemCollection: IVehicleControlItem[],
    ...vehicleControlItemsToCheck: (IVehicleControlItem | null | undefined)[]
  ): IVehicleControlItem[] {
    const vehicleControlItems: IVehicleControlItem[] = vehicleControlItemsToCheck.filter(isPresent);
    if (vehicleControlItems.length > 0) {
      const vehicleControlItemCollectionIdentifiers = vehicleControlItemCollection.map(
        vehicleControlItemItem => getVehicleControlItemIdentifier(vehicleControlItemItem)!
      );
      const vehicleControlItemsToAdd = vehicleControlItems.filter(vehicleControlItemItem => {
        const vehicleControlItemIdentifier = getVehicleControlItemIdentifier(vehicleControlItemItem);
        if (vehicleControlItemIdentifier == null || vehicleControlItemCollectionIdentifiers.includes(vehicleControlItemIdentifier)) {
          return false;
        }
        vehicleControlItemCollectionIdentifiers.push(vehicleControlItemIdentifier);
        return true;
      });
      return [...vehicleControlItemsToAdd, ...vehicleControlItemCollection];
    }
    return vehicleControlItemCollection;
  }

  protected convertDateFromClient(vehicleControlItem: IVehicleControlItem): IVehicleControlItem {
    return Object.assign({}, vehicleControlItem, {
      vehicleControlItemCTEDate: vehicleControlItem.vehicleControlItemCTEDate?.isValid()
        ? vehicleControlItem.vehicleControlItemCTEDate.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleControlItemCTEDate = res.body.vehicleControlItemCTEDate ? dayjs(res.body.vehicleControlItemCTEDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleControlItem: IVehicleControlItem) => {
        vehicleControlItem.vehicleControlItemCTEDate = vehicleControlItem.vehicleControlItemCTEDate
          ? dayjs(vehicleControlItem.vehicleControlItemCTEDate)
          : undefined;
      });
    }
    return res;
  }
}
