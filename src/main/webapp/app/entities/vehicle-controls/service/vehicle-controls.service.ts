import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleControls, getVehicleControlsIdentifier } from '../vehicle-controls.model';

export type EntityResponseType = HttpResponse<IVehicleControls>;
export type EntityArrayResponseType = HttpResponse<IVehicleControls[]>;

@Injectable({ providedIn: 'root' })
export class VehicleControlsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-controls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleControls: IVehicleControls): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControls);
    return this.http
      .post<IVehicleControls>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleControls: IVehicleControls): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControls);
    return this.http
      .put<IVehicleControls>(`${this.resourceUrl}/${getVehicleControlsIdentifier(vehicleControls) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleControls: IVehicleControls): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControls);
    return this.http
      .patch<IVehicleControls>(`${this.resourceUrl}/${getVehicleControlsIdentifier(vehicleControls) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleControls>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleControls[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleControlsToCollectionIfMissing(
    vehicleControlsCollection: IVehicleControls[],
    ...vehicleControlsToCheck: (IVehicleControls | null | undefined)[]
  ): IVehicleControls[] {
    const vehicleControls: IVehicleControls[] = vehicleControlsToCheck.filter(isPresent);
    if (vehicleControls.length > 0) {
      const vehicleControlsCollectionIdentifiers = vehicleControlsCollection.map(
        vehicleControlsItem => getVehicleControlsIdentifier(vehicleControlsItem)!
      );
      const vehicleControlsToAdd = vehicleControls.filter(vehicleControlsItem => {
        const vehicleControlsIdentifier = getVehicleControlsIdentifier(vehicleControlsItem);
        if (vehicleControlsIdentifier == null || vehicleControlsCollectionIdentifiers.includes(vehicleControlsIdentifier)) {
          return false;
        }
        vehicleControlsCollectionIdentifiers.push(vehicleControlsIdentifier);
        return true;
      });
      return [...vehicleControlsToAdd, ...vehicleControlsCollection];
    }
    return vehicleControlsCollection;
  }

  protected convertDateFromClient(vehicleControls: IVehicleControls): IVehicleControls {
    return Object.assign({}, vehicleControls, {
      vehicleControlDate: vehicleControls.vehicleControlDate?.isValid()
        ? vehicleControls.vehicleControlDate.format(DATE_FORMAT)
        : undefined,
      vehicleControlMaximumDeliveryDate: vehicleControls.vehicleControlMaximumDeliveryDate?.isValid()
        ? vehicleControls.vehicleControlMaximumDeliveryDate.format(DATE_FORMAT)
        : undefined,
      vehicleControlCollectionForecast: vehicleControls.vehicleControlCollectionForecast?.isValid()
        ? vehicleControls.vehicleControlCollectionForecast.format(DATE_FORMAT)
        : undefined,
      vehicleControlCollectionDeliveryForecast: vehicleControls.vehicleControlCollectionDeliveryForecast?.isValid()
        ? vehicleControls.vehicleControlCollectionDeliveryForecast.format(DATE_FORMAT)
        : undefined,
      vehicleControlDateCollected: vehicleControls.vehicleControlDateCollected?.isValid()
        ? vehicleControls.vehicleControlDateCollected.format(DATE_FORMAT)
        : undefined,
      vehicleControlDeliveryDate: vehicleControls.vehicleControlDeliveryDate?.isValid()
        ? vehicleControls.vehicleControlDeliveryDate.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleControlDate = res.body.vehicleControlDate ? dayjs(res.body.vehicleControlDate) : undefined;
      res.body.vehicleControlMaximumDeliveryDate = res.body.vehicleControlMaximumDeliveryDate
        ? dayjs(res.body.vehicleControlMaximumDeliveryDate)
        : undefined;
      res.body.vehicleControlCollectionForecast = res.body.vehicleControlCollectionForecast
        ? dayjs(res.body.vehicleControlCollectionForecast)
        : undefined;
      res.body.vehicleControlCollectionDeliveryForecast = res.body.vehicleControlCollectionDeliveryForecast
        ? dayjs(res.body.vehicleControlCollectionDeliveryForecast)
        : undefined;
      res.body.vehicleControlDateCollected = res.body.vehicleControlDateCollected ? dayjs(res.body.vehicleControlDateCollected) : undefined;
      res.body.vehicleControlDeliveryDate = res.body.vehicleControlDeliveryDate ? dayjs(res.body.vehicleControlDeliveryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleControls: IVehicleControls) => {
        vehicleControls.vehicleControlDate = vehicleControls.vehicleControlDate ? dayjs(vehicleControls.vehicleControlDate) : undefined;
        vehicleControls.vehicleControlMaximumDeliveryDate = vehicleControls.vehicleControlMaximumDeliveryDate
          ? dayjs(vehicleControls.vehicleControlMaximumDeliveryDate)
          : undefined;
        vehicleControls.vehicleControlCollectionForecast = vehicleControls.vehicleControlCollectionForecast
          ? dayjs(vehicleControls.vehicleControlCollectionForecast)
          : undefined;
        vehicleControls.vehicleControlCollectionDeliveryForecast = vehicleControls.vehicleControlCollectionDeliveryForecast
          ? dayjs(vehicleControls.vehicleControlCollectionDeliveryForecast)
          : undefined;
        vehicleControls.vehicleControlDateCollected = vehicleControls.vehicleControlDateCollected
          ? dayjs(vehicleControls.vehicleControlDateCollected)
          : undefined;
        vehicleControls.vehicleControlDeliveryDate = vehicleControls.vehicleControlDeliveryDate
          ? dayjs(vehicleControls.vehicleControlDeliveryDate)
          : undefined;
      });
    }
    return res;
  }
}
