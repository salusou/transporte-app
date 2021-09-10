import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdministrativeFeesRanges, getAdministrativeFeesRangesIdentifier } from '../administrative-fees-ranges.model';

export type EntityResponseType = HttpResponse<IAdministrativeFeesRanges>;
export type EntityArrayResponseType = HttpResponse<IAdministrativeFeesRanges[]>;

@Injectable({ providedIn: 'root' })
export class AdministrativeFeesRangesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/administrative-fees-ranges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(administrativeFeesRanges: IAdministrativeFeesRanges): Observable<EntityResponseType> {
    return this.http.post<IAdministrativeFeesRanges>(this.resourceUrl, administrativeFeesRanges, { observe: 'response' });
  }

  update(administrativeFeesRanges: IAdministrativeFeesRanges): Observable<EntityResponseType> {
    return this.http.put<IAdministrativeFeesRanges>(
      `${this.resourceUrl}/${getAdministrativeFeesRangesIdentifier(administrativeFeesRanges) as number}`,
      administrativeFeesRanges,
      { observe: 'response' }
    );
  }

  partialUpdate(administrativeFeesRanges: IAdministrativeFeesRanges): Observable<EntityResponseType> {
    return this.http.patch<IAdministrativeFeesRanges>(
      `${this.resourceUrl}/${getAdministrativeFeesRangesIdentifier(administrativeFeesRanges) as number}`,
      administrativeFeesRanges,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdministrativeFeesRanges>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdministrativeFeesRanges[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdministrativeFeesRangesToCollectionIfMissing(
    administrativeFeesRangesCollection: IAdministrativeFeesRanges[],
    ...administrativeFeesRangesToCheck: (IAdministrativeFeesRanges | null | undefined)[]
  ): IAdministrativeFeesRanges[] {
    const administrativeFeesRanges: IAdministrativeFeesRanges[] = administrativeFeesRangesToCheck.filter(isPresent);
    if (administrativeFeesRanges.length > 0) {
      const administrativeFeesRangesCollectionIdentifiers = administrativeFeesRangesCollection.map(
        administrativeFeesRangesItem => getAdministrativeFeesRangesIdentifier(administrativeFeesRangesItem)!
      );
      const administrativeFeesRangesToAdd = administrativeFeesRanges.filter(administrativeFeesRangesItem => {
        const administrativeFeesRangesIdentifier = getAdministrativeFeesRangesIdentifier(administrativeFeesRangesItem);
        if (
          administrativeFeesRangesIdentifier == null ||
          administrativeFeesRangesCollectionIdentifiers.includes(administrativeFeesRangesIdentifier)
        ) {
          return false;
        }
        administrativeFeesRangesCollectionIdentifiers.push(administrativeFeesRangesIdentifier);
        return true;
      });
      return [...administrativeFeesRangesToAdd, ...administrativeFeesRangesCollection];
    }
    return administrativeFeesRangesCollection;
  }
}
