import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFees, getFeesIdentifier } from '../fees.model';

export type EntityResponseType = HttpResponse<IFees>;
export type EntityArrayResponseType = HttpResponse<IFees[]>;

@Injectable({ providedIn: 'root' })
export class FeesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fees: IFees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fees);
    return this.http
      .post<IFees>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fees: IFees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fees);
    return this.http
      .put<IFees>(`${this.resourceUrl}/${getFeesIdentifier(fees) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fees: IFees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fees);
    return this.http
      .patch<IFees>(`${this.resourceUrl}/${getFeesIdentifier(fees) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFees>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFees[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFeesToCollectionIfMissing(feesCollection: IFees[], ...feesToCheck: (IFees | null | undefined)[]): IFees[] {
    const fees: IFees[] = feesToCheck.filter(isPresent);
    if (fees.length > 0) {
      const feesCollectionIdentifiers = feesCollection.map(feesItem => getFeesIdentifier(feesItem)!);
      const feesToAdd = fees.filter(feesItem => {
        const feesIdentifier = getFeesIdentifier(feesItem);
        if (feesIdentifier == null || feesCollectionIdentifiers.includes(feesIdentifier)) {
          return false;
        }
        feesCollectionIdentifiers.push(feesIdentifier);
        return true;
      });
      return [...feesToAdd, ...feesCollection];
    }
    return feesCollection;
  }

  protected convertDateFromClient(fees: IFees): IFees {
    return Object.assign({}, fees, {
      feeDate: fees.feeDate?.isValid() ? fees.feeDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.feeDate = res.body.feeDate ? dayjs(res.body.feeDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fees: IFees) => {
        fees.feeDate = fees.feeDate ? dayjs(fees.feeDate) : undefined;
      });
    }
    return res;
  }
}
