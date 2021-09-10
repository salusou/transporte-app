import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleLocationStatus, getVehicleLocationStatusIdentifier } from '../vehicle-location-status.model';

export type EntityResponseType = HttpResponse<IVehicleLocationStatus>;
export type EntityArrayResponseType = HttpResponse<IVehicleLocationStatus[]>;

@Injectable({ providedIn: 'root' })
export class VehicleLocationStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-location-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleLocationStatus: IVehicleLocationStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleLocationStatus);
    return this.http
      .post<IVehicleLocationStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleLocationStatus: IVehicleLocationStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleLocationStatus);
    return this.http
      .put<IVehicleLocationStatus>(`${this.resourceUrl}/${getVehicleLocationStatusIdentifier(vehicleLocationStatus) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleLocationStatus: IVehicleLocationStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleLocationStatus);
    return this.http
      .patch<IVehicleLocationStatus>(`${this.resourceUrl}/${getVehicleLocationStatusIdentifier(vehicleLocationStatus) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleLocationStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleLocationStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleLocationStatusToCollectionIfMissing(
    vehicleLocationStatusCollection: IVehicleLocationStatus[],
    ...vehicleLocationStatusesToCheck: (IVehicleLocationStatus | null | undefined)[]
  ): IVehicleLocationStatus[] {
    const vehicleLocationStatuses: IVehicleLocationStatus[] = vehicleLocationStatusesToCheck.filter(isPresent);
    if (vehicleLocationStatuses.length > 0) {
      const vehicleLocationStatusCollectionIdentifiers = vehicleLocationStatusCollection.map(
        vehicleLocationStatusItem => getVehicleLocationStatusIdentifier(vehicleLocationStatusItem)!
      );
      const vehicleLocationStatusesToAdd = vehicleLocationStatuses.filter(vehicleLocationStatusItem => {
        const vehicleLocationStatusIdentifier = getVehicleLocationStatusIdentifier(vehicleLocationStatusItem);
        if (
          vehicleLocationStatusIdentifier == null ||
          vehicleLocationStatusCollectionIdentifiers.includes(vehicleLocationStatusIdentifier)
        ) {
          return false;
        }
        vehicleLocationStatusCollectionIdentifiers.push(vehicleLocationStatusIdentifier);
        return true;
      });
      return [...vehicleLocationStatusesToAdd, ...vehicleLocationStatusCollection];
    }
    return vehicleLocationStatusCollection;
  }

  protected convertDateFromClient(vehicleLocationStatus: IVehicleLocationStatus): IVehicleLocationStatus {
    return Object.assign({}, vehicleLocationStatus, {
      vehicleLocationStatusDate: vehicleLocationStatus.vehicleLocationStatusDate?.isValid()
        ? vehicleLocationStatus.vehicleLocationStatusDate.toJSON()
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleLocationStatusDate = res.body.vehicleLocationStatusDate ? dayjs(res.body.vehicleLocationStatusDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleLocationStatus: IVehicleLocationStatus) => {
        vehicleLocationStatus.vehicleLocationStatusDate = vehicleLocationStatus.vehicleLocationStatusDate
          ? dayjs(vehicleLocationStatus.vehicleLocationStatusDate)
          : undefined;
      });
    }
    return res;
  }
}
