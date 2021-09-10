import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParkingSectorSpace, getParkingSectorSpaceIdentifier } from '../parking-sector-space.model';

export type EntityResponseType = HttpResponse<IParkingSectorSpace>;
export type EntityArrayResponseType = HttpResponse<IParkingSectorSpace[]>;

@Injectable({ providedIn: 'root' })
export class ParkingSectorSpaceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parking-sector-spaces');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parkingSectorSpace: IParkingSectorSpace): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parkingSectorSpace);
    return this.http
      .post<IParkingSectorSpace>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(parkingSectorSpace: IParkingSectorSpace): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parkingSectorSpace);
    return this.http
      .put<IParkingSectorSpace>(`${this.resourceUrl}/${getParkingSectorSpaceIdentifier(parkingSectorSpace) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(parkingSectorSpace: IParkingSectorSpace): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parkingSectorSpace);
    return this.http
      .patch<IParkingSectorSpace>(`${this.resourceUrl}/${getParkingSectorSpaceIdentifier(parkingSectorSpace) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParkingSectorSpace>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParkingSectorSpace[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParkingSectorSpaceToCollectionIfMissing(
    parkingSectorSpaceCollection: IParkingSectorSpace[],
    ...parkingSectorSpacesToCheck: (IParkingSectorSpace | null | undefined)[]
  ): IParkingSectorSpace[] {
    const parkingSectorSpaces: IParkingSectorSpace[] = parkingSectorSpacesToCheck.filter(isPresent);
    if (parkingSectorSpaces.length > 0) {
      const parkingSectorSpaceCollectionIdentifiers = parkingSectorSpaceCollection.map(
        parkingSectorSpaceItem => getParkingSectorSpaceIdentifier(parkingSectorSpaceItem)!
      );
      const parkingSectorSpacesToAdd = parkingSectorSpaces.filter(parkingSectorSpaceItem => {
        const parkingSectorSpaceIdentifier = getParkingSectorSpaceIdentifier(parkingSectorSpaceItem);
        if (parkingSectorSpaceIdentifier == null || parkingSectorSpaceCollectionIdentifiers.includes(parkingSectorSpaceIdentifier)) {
          return false;
        }
        parkingSectorSpaceCollectionIdentifiers.push(parkingSectorSpaceIdentifier);
        return true;
      });
      return [...parkingSectorSpacesToAdd, ...parkingSectorSpaceCollection];
    }
    return parkingSectorSpaceCollection;
  }

  protected convertDateFromClient(parkingSectorSpace: IParkingSectorSpace): IParkingSectorSpace {
    return Object.assign({}, parkingSectorSpace, {
      parkingEntryDate: parkingSectorSpace.parkingEntryDate?.isValid()
        ? parkingSectorSpace.parkingEntryDate.format(DATE_FORMAT)
        : undefined,
      parkingDepartureDate: parkingSectorSpace.parkingDepartureDate?.isValid()
        ? parkingSectorSpace.parkingDepartureDate.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.parkingEntryDate = res.body.parkingEntryDate ? dayjs(res.body.parkingEntryDate) : undefined;
      res.body.parkingDepartureDate = res.body.parkingDepartureDate ? dayjs(res.body.parkingDepartureDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((parkingSectorSpace: IParkingSectorSpace) => {
        parkingSectorSpace.parkingEntryDate = parkingSectorSpace.parkingEntryDate ? dayjs(parkingSectorSpace.parkingEntryDate) : undefined;
        parkingSectorSpace.parkingDepartureDate = parkingSectorSpace.parkingDepartureDate
          ? dayjs(parkingSectorSpace.parkingDepartureDate)
          : undefined;
      });
    }
    return res;
  }
}
