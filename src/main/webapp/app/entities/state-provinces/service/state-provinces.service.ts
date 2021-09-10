import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStateProvinces, getStateProvincesIdentifier } from '../state-provinces.model';

export type EntityResponseType = HttpResponse<IStateProvinces>;
export type EntityArrayResponseType = HttpResponse<IStateProvinces[]>;

@Injectable({ providedIn: 'root' })
export class StateProvincesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/state-provinces');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stateProvinces: IStateProvinces): Observable<EntityResponseType> {
    return this.http.post<IStateProvinces>(this.resourceUrl, stateProvinces, { observe: 'response' });
  }

  update(stateProvinces: IStateProvinces): Observable<EntityResponseType> {
    return this.http.put<IStateProvinces>(`${this.resourceUrl}/${getStateProvincesIdentifier(stateProvinces) as number}`, stateProvinces, {
      observe: 'response',
    });
  }

  partialUpdate(stateProvinces: IStateProvinces): Observable<EntityResponseType> {
    return this.http.patch<IStateProvinces>(
      `${this.resourceUrl}/${getStateProvincesIdentifier(stateProvinces) as number}`,
      stateProvinces,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStateProvinces>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStateProvinces[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStateProvincesToCollectionIfMissing(
    stateProvincesCollection: IStateProvinces[],
    ...stateProvincesToCheck: (IStateProvinces | null | undefined)[]
  ): IStateProvinces[] {
    const stateProvinces: IStateProvinces[] = stateProvincesToCheck.filter(isPresent);
    if (stateProvinces.length > 0) {
      const stateProvincesCollectionIdentifiers = stateProvincesCollection.map(
        stateProvincesItem => getStateProvincesIdentifier(stateProvincesItem)!
      );
      const stateProvincesToAdd = stateProvinces.filter(stateProvincesItem => {
        const stateProvincesIdentifier = getStateProvincesIdentifier(stateProvincesItem);
        if (stateProvincesIdentifier == null || stateProvincesCollectionIdentifiers.includes(stateProvincesIdentifier)) {
          return false;
        }
        stateProvincesCollectionIdentifiers.push(stateProvincesIdentifier);
        return true;
      });
      return [...stateProvincesToAdd, ...stateProvincesCollection];
    }
    return stateProvincesCollection;
  }
}
