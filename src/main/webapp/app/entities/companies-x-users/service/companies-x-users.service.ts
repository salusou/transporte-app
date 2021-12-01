import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompaniesXUsers, getCompaniesXUsersIdentifier } from '../companies-x-users.model';

export type EntityResponseType = HttpResponse<ICompaniesXUsers>;
export type EntityArrayResponseType = HttpResponse<ICompaniesXUsers[]>;

@Injectable({ providedIn: 'root' })
export class CompaniesXUsersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/companies-x-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companiesXUsers: ICompaniesXUsers): Observable<EntityResponseType> {
    return this.http.post<ICompaniesXUsers>(this.resourceUrl, companiesXUsers, { observe: 'response' });
  }

  update(companiesXUsers: ICompaniesXUsers): Observable<EntityResponseType> {
    return this.http.put<ICompaniesXUsers>(
      `${this.resourceUrl}/${getCompaniesXUsersIdentifier(companiesXUsers) as number}`,
      companiesXUsers,
      { observe: 'response' }
    );
  }

  partialUpdate(companiesXUsers: ICompaniesXUsers): Observable<EntityResponseType> {
    return this.http.patch<ICompaniesXUsers>(
      `${this.resourceUrl}/${getCompaniesXUsersIdentifier(companiesXUsers) as number}`,
      companiesXUsers,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompaniesXUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompaniesXUsers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCompaniesXUsersToCollectionIfMissing(
    companiesXUsersCollection: ICompaniesXUsers[],
    ...companiesXUsersToCheck: (ICompaniesXUsers | null | undefined)[]
  ): ICompaniesXUsers[] {
    const companiesXUsers: ICompaniesXUsers[] = companiesXUsersToCheck.filter(isPresent);
    if (companiesXUsers.length > 0) {
      const companiesXUsersCollectionIdentifiers = companiesXUsersCollection.map(
        companiesXUsersItem => getCompaniesXUsersIdentifier(companiesXUsersItem)!
      );
      const companiesXUsersToAdd = companiesXUsers.filter(companiesXUsersItem => {
        const companiesXUsersIdentifier = getCompaniesXUsersIdentifier(companiesXUsersItem);
        if (companiesXUsersIdentifier == null || companiesXUsersCollectionIdentifiers.includes(companiesXUsersIdentifier)) {
          return false;
        }
        companiesXUsersCollectionIdentifiers.push(companiesXUsersIdentifier);
        return true;
      });
      return [...companiesXUsersToAdd, ...companiesXUsersCollection];
    }
    return companiesXUsersCollection;
  }
}
