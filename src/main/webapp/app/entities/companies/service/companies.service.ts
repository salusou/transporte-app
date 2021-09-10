import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanies, getCompaniesIdentifier } from '../companies.model';

export type EntityResponseType = HttpResponse<ICompanies>;
export type EntityArrayResponseType = HttpResponse<ICompanies[]>;

@Injectable({ providedIn: 'root' })
export class CompaniesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/companies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companies: ICompanies): Observable<EntityResponseType> {
    return this.http.post<ICompanies>(this.resourceUrl, companies, { observe: 'response' });
  }

  update(companies: ICompanies): Observable<EntityResponseType> {
    return this.http.put<ICompanies>(`${this.resourceUrl}/${getCompaniesIdentifier(companies) as number}`, companies, {
      observe: 'response',
    });
  }

  partialUpdate(companies: ICompanies): Observable<EntityResponseType> {
    return this.http.patch<ICompanies>(`${this.resourceUrl}/${getCompaniesIdentifier(companies) as number}`, companies, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanies>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanies[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCompaniesToCollectionIfMissing(
    companiesCollection: ICompanies[],
    ...companiesToCheck: (ICompanies | null | undefined)[]
  ): ICompanies[] {
    const companies: ICompanies[] = companiesToCheck.filter(isPresent);
    if (companies.length > 0) {
      const companiesCollectionIdentifiers = companiesCollection.map(companiesItem => getCompaniesIdentifier(companiesItem)!);
      const companiesToAdd = companies.filter(companiesItem => {
        const companiesIdentifier = getCompaniesIdentifier(companiesItem);
        if (companiesIdentifier == null || companiesCollectionIdentifiers.includes(companiesIdentifier)) {
          return false;
        }
        companiesCollectionIdentifiers.push(companiesIdentifier);
        return true;
      });
      return [...companiesToAdd, ...companiesCollection];
    }
    return companiesCollection;
  }
}
