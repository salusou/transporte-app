import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleControlHistory, getVehicleControlHistoryIdentifier } from '../vehicle-control-history.model';

export type EntityResponseType = HttpResponse<IVehicleControlHistory>;
export type EntityArrayResponseType = HttpResponse<IVehicleControlHistory[]>;

@Injectable({ providedIn: 'root' })
export class VehicleControlHistoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-control-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleControlHistory: IVehicleControlHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlHistory);
    return this.http
      .post<IVehicleControlHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleControlHistory: IVehicleControlHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlHistory);
    return this.http
      .put<IVehicleControlHistory>(`${this.resourceUrl}/${getVehicleControlHistoryIdentifier(vehicleControlHistory) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleControlHistory: IVehicleControlHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlHistory);
    return this.http
      .patch<IVehicleControlHistory>(`${this.resourceUrl}/${getVehicleControlHistoryIdentifier(vehicleControlHistory) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleControlHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleControlHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleControlHistoryToCollectionIfMissing(
    vehicleControlHistoryCollection: IVehicleControlHistory[],
    ...vehicleControlHistoriesToCheck: (IVehicleControlHistory | null | undefined)[]
  ): IVehicleControlHistory[] {
    const vehicleControlHistories: IVehicleControlHistory[] = vehicleControlHistoriesToCheck.filter(isPresent);
    if (vehicleControlHistories.length > 0) {
      const vehicleControlHistoryCollectionIdentifiers = vehicleControlHistoryCollection.map(
        vehicleControlHistoryItem => getVehicleControlHistoryIdentifier(vehicleControlHistoryItem)!
      );
      const vehicleControlHistoriesToAdd = vehicleControlHistories.filter(vehicleControlHistoryItem => {
        const vehicleControlHistoryIdentifier = getVehicleControlHistoryIdentifier(vehicleControlHistoryItem);
        if (
          vehicleControlHistoryIdentifier == null ||
          vehicleControlHistoryCollectionIdentifiers.includes(vehicleControlHistoryIdentifier)
        ) {
          return false;
        }
        vehicleControlHistoryCollectionIdentifiers.push(vehicleControlHistoryIdentifier);
        return true;
      });
      return [...vehicleControlHistoriesToAdd, ...vehicleControlHistoryCollection];
    }
    return vehicleControlHistoryCollection;
  }

  protected convertDateFromClient(vehicleControlHistory: IVehicleControlHistory): IVehicleControlHistory {
    return Object.assign({}, vehicleControlHistory, {
      vehicleControlHistoryDate: vehicleControlHistory.vehicleControlHistoryDate?.isValid()
        ? vehicleControlHistory.vehicleControlHistoryDate.toJSON()
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleControlHistoryDate = res.body.vehicleControlHistoryDate ? dayjs(res.body.vehicleControlHistoryDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleControlHistory: IVehicleControlHistory) => {
        vehicleControlHistory.vehicleControlHistoryDate = vehicleControlHistory.vehicleControlHistoryDate
          ? dayjs(vehicleControlHistory.vehicleControlHistoryDate)
          : undefined;
      });
    }
    return res;
  }
}
