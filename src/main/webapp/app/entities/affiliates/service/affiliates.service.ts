import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAffiliates, getAffiliatesIdentifier } from '../affiliates.model';

export type EntityResponseType = HttpResponse<IAffiliates>;
export type EntityArrayResponseType = HttpResponse<IAffiliates[]>;

@Injectable({ providedIn: 'root' })
export class AffiliatesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/affiliates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(affiliates: IAffiliates): Observable<EntityResponseType> {
    return this.http.post<IAffiliates>(this.resourceUrl, affiliates, { observe: 'response' });
  }

  update(affiliates: IAffiliates): Observable<EntityResponseType> {
    return this.http.put<IAffiliates>(`${this.resourceUrl}/${getAffiliatesIdentifier(affiliates) as number}`, affiliates, {
      observe: 'response',
    });
  }

  partialUpdate(affiliates: IAffiliates): Observable<EntityResponseType> {
    return this.http.patch<IAffiliates>(`${this.resourceUrl}/${getAffiliatesIdentifier(affiliates) as number}`, affiliates, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAffiliates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAffiliates[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAffiliatesToCollectionIfMissing(
    affiliatesCollection: IAffiliates[],
    ...affiliatesToCheck: (IAffiliates | null | undefined)[]
  ): IAffiliates[] {
    const affiliates: IAffiliates[] = affiliatesToCheck.filter(isPresent);
    if (affiliates.length > 0) {
      const affiliatesCollectionIdentifiers = affiliatesCollection.map(affiliatesItem => getAffiliatesIdentifier(affiliatesItem)!);
      const affiliatesToAdd = affiliates.filter(affiliatesItem => {
        const affiliatesIdentifier = getAffiliatesIdentifier(affiliatesItem);
        if (affiliatesIdentifier == null || affiliatesCollectionIdentifiers.includes(affiliatesIdentifier)) {
          return false;
        }
        affiliatesCollectionIdentifiers.push(affiliatesIdentifier);
        return true;
      });
      return [...affiliatesToAdd, ...affiliatesCollection];
    }
    return affiliatesCollection;
  }
}
