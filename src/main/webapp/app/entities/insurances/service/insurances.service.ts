import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInsurances, getInsurancesIdentifier } from '../insurances.model';

export type EntityResponseType = HttpResponse<IInsurances>;
export type EntityArrayResponseType = HttpResponse<IInsurances[]>;

@Injectable({ providedIn: 'root' })
export class InsurancesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/insurances');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(insurances: IInsurances): Observable<EntityResponseType> {
    return this.http.post<IInsurances>(this.resourceUrl, insurances, { observe: 'response' });
  }

  update(insurances: IInsurances): Observable<EntityResponseType> {
    return this.http.put<IInsurances>(`${this.resourceUrl}/${getInsurancesIdentifier(insurances) as number}`, insurances, {
      observe: 'response',
    });
  }

  partialUpdate(insurances: IInsurances): Observable<EntityResponseType> {
    return this.http.patch<IInsurances>(`${this.resourceUrl}/${getInsurancesIdentifier(insurances) as number}`, insurances, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInsurances>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsurances[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInsurancesToCollectionIfMissing(
    insurancesCollection: IInsurances[],
    ...insurancesToCheck: (IInsurances | null | undefined)[]
  ): IInsurances[] {
    const insurances: IInsurances[] = insurancesToCheck.filter(isPresent);
    if (insurances.length > 0) {
      const insurancesCollectionIdentifiers = insurancesCollection.map(insurancesItem => getInsurancesIdentifier(insurancesItem)!);
      const insurancesToAdd = insurances.filter(insurancesItem => {
        const insurancesIdentifier = getInsurancesIdentifier(insurancesItem);
        if (insurancesIdentifier == null || insurancesCollectionIdentifiers.includes(insurancesIdentifier)) {
          return false;
        }
        insurancesCollectionIdentifiers.push(insurancesIdentifier);
        return true;
      });
      return [...insurancesToAdd, ...insurancesCollection];
    }
    return insurancesCollection;
  }
}
