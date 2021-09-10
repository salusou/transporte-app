import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHousing, getHousingIdentifier } from '../housing.model';

export type EntityResponseType = HttpResponse<IHousing>;
export type EntityArrayResponseType = HttpResponse<IHousing[]>;

@Injectable({ providedIn: 'root' })
export class HousingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/housings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(housing: IHousing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(housing);
    return this.http
      .post<IHousing>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(housing: IHousing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(housing);
    return this.http
      .put<IHousing>(`${this.resourceUrl}/${getHousingIdentifier(housing) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(housing: IHousing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(housing);
    return this.http
      .patch<IHousing>(`${this.resourceUrl}/${getHousingIdentifier(housing) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHousing>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHousing[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHousingToCollectionIfMissing(housingCollection: IHousing[], ...housingsToCheck: (IHousing | null | undefined)[]): IHousing[] {
    const housings: IHousing[] = housingsToCheck.filter(isPresent);
    if (housings.length > 0) {
      const housingCollectionIdentifiers = housingCollection.map(housingItem => getHousingIdentifier(housingItem)!);
      const housingsToAdd = housings.filter(housingItem => {
        const housingIdentifier = getHousingIdentifier(housingItem);
        if (housingIdentifier == null || housingCollectionIdentifiers.includes(housingIdentifier)) {
          return false;
        }
        housingCollectionIdentifiers.push(housingIdentifier);
        return true;
      });
      return [...housingsToAdd, ...housingCollection];
    }
    return housingCollection;
  }

  protected convertDateFromClient(housing: IHousing): IHousing {
    return Object.assign({}, housing, {
      housingDate: housing.housingDate?.isValid() ? housing.housingDate.format(DATE_FORMAT) : undefined,
      housingEntranceDate: housing.housingEntranceDate?.isValid() ? housing.housingEntranceDate.toJSON() : undefined,
      housingExit: housing.housingExit?.isValid() ? housing.housingExit.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.housingDate = res.body.housingDate ? dayjs(res.body.housingDate) : undefined;
      res.body.housingEntranceDate = res.body.housingEntranceDate ? dayjs(res.body.housingEntranceDate) : undefined;
      res.body.housingExit = res.body.housingExit ? dayjs(res.body.housingExit) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((housing: IHousing) => {
        housing.housingDate = housing.housingDate ? dayjs(housing.housingDate) : undefined;
        housing.housingEntranceDate = housing.housingEntranceDate ? dayjs(housing.housingEntranceDate) : undefined;
        housing.housingExit = housing.housingExit ? dayjs(housing.housingExit) : undefined;
      });
    }
    return res;
  }
}
