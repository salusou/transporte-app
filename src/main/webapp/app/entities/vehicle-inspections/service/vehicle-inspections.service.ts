import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleInspections, getVehicleInspectionsIdentifier } from '../vehicle-inspections.model';

export type EntityResponseType = HttpResponse<IVehicleInspections>;
export type EntityArrayResponseType = HttpResponse<IVehicleInspections[]>;

@Injectable({ providedIn: 'root' })
export class VehicleInspectionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-inspections');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleInspections: IVehicleInspections): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleInspections);
    return this.http
      .post<IVehicleInspections>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleInspections: IVehicleInspections): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleInspections);
    return this.http
      .put<IVehicleInspections>(`${this.resourceUrl}/${getVehicleInspectionsIdentifier(vehicleInspections) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleInspections: IVehicleInspections): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleInspections);
    return this.http
      .patch<IVehicleInspections>(`${this.resourceUrl}/${getVehicleInspectionsIdentifier(vehicleInspections) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleInspections>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleInspections[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleInspectionsToCollectionIfMissing(
    vehicleInspectionsCollection: IVehicleInspections[],
    ...vehicleInspectionsToCheck: (IVehicleInspections | null | undefined)[]
  ): IVehicleInspections[] {
    const vehicleInspections: IVehicleInspections[] = vehicleInspectionsToCheck.filter(isPresent);
    if (vehicleInspections.length > 0) {
      const vehicleInspectionsCollectionIdentifiers = vehicleInspectionsCollection.map(
        vehicleInspectionsItem => getVehicleInspectionsIdentifier(vehicleInspectionsItem)!
      );
      const vehicleInspectionsToAdd = vehicleInspections.filter(vehicleInspectionsItem => {
        const vehicleInspectionsIdentifier = getVehicleInspectionsIdentifier(vehicleInspectionsItem);
        if (vehicleInspectionsIdentifier == null || vehicleInspectionsCollectionIdentifiers.includes(vehicleInspectionsIdentifier)) {
          return false;
        }
        vehicleInspectionsCollectionIdentifiers.push(vehicleInspectionsIdentifier);
        return true;
      });
      return [...vehicleInspectionsToAdd, ...vehicleInspectionsCollection];
    }
    return vehicleInspectionsCollection;
  }

  protected convertDateFromClient(vehicleInspections: IVehicleInspections): IVehicleInspections {
    return Object.assign({}, vehicleInspections, {
      vehicleInspectionDate: vehicleInspections.vehicleInspectionDate?.isValid()
        ? vehicleInspections.vehicleInspectionDate.toJSON()
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleInspectionDate = res.body.vehicleInspectionDate ? dayjs(res.body.vehicleInspectionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleInspections: IVehicleInspections) => {
        vehicleInspections.vehicleInspectionDate = vehicleInspections.vehicleInspectionDate
          ? dayjs(vehicleInspections.vehicleInspectionDate)
          : undefined;
      });
    }
    return res;
  }
}
