import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPositions, getPositionsIdentifier } from '../positions.model';

export type EntityResponseType = HttpResponse<IPositions>;
export type EntityArrayResponseType = HttpResponse<IPositions[]>;

@Injectable({ providedIn: 'root' })
export class PositionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/positions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(positions: IPositions): Observable<EntityResponseType> {
    return this.http.post<IPositions>(this.resourceUrl, positions, { observe: 'response' });
  }

  update(positions: IPositions): Observable<EntityResponseType> {
    return this.http.put<IPositions>(`${this.resourceUrl}/${getPositionsIdentifier(positions) as number}`, positions, {
      observe: 'response',
    });
  }

  partialUpdate(positions: IPositions): Observable<EntityResponseType> {
    return this.http.patch<IPositions>(`${this.resourceUrl}/${getPositionsIdentifier(positions) as number}`, positions, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPositions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPositions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPositionsToCollectionIfMissing(
    positionsCollection: IPositions[],
    ...positionsToCheck: (IPositions | null | undefined)[]
  ): IPositions[] {
    const positions: IPositions[] = positionsToCheck.filter(isPresent);
    if (positions.length > 0) {
      const positionsCollectionIdentifiers = positionsCollection.map(positionsItem => getPositionsIdentifier(positionsItem)!);
      const positionsToAdd = positions.filter(positionsItem => {
        const positionsIdentifier = getPositionsIdentifier(positionsItem);
        if (positionsIdentifier == null || positionsCollectionIdentifiers.includes(positionsIdentifier)) {
          return false;
        }
        positionsCollectionIdentifiers.push(positionsIdentifier);
        return true;
      });
      return [...positionsToAdd, ...positionsCollection];
    }
    return positionsCollection;
  }
}
