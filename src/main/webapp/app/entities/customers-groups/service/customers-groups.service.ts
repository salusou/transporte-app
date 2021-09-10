import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomersGroups, getCustomersGroupsIdentifier } from '../customers-groups.model';

export type EntityResponseType = HttpResponse<ICustomersGroups>;
export type EntityArrayResponseType = HttpResponse<ICustomersGroups[]>;

@Injectable({ providedIn: 'root' })
export class CustomersGroupsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customers-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customersGroups: ICustomersGroups): Observable<EntityResponseType> {
    return this.http.post<ICustomersGroups>(this.resourceUrl, customersGroups, { observe: 'response' });
  }

  update(customersGroups: ICustomersGroups): Observable<EntityResponseType> {
    return this.http.put<ICustomersGroups>(
      `${this.resourceUrl}/${getCustomersGroupsIdentifier(customersGroups) as number}`,
      customersGroups,
      { observe: 'response' }
    );
  }

  partialUpdate(customersGroups: ICustomersGroups): Observable<EntityResponseType> {
    return this.http.patch<ICustomersGroups>(
      `${this.resourceUrl}/${getCustomersGroupsIdentifier(customersGroups) as number}`,
      customersGroups,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomersGroups>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomersGroups[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomersGroupsToCollectionIfMissing(
    customersGroupsCollection: ICustomersGroups[],
    ...customersGroupsToCheck: (ICustomersGroups | null | undefined)[]
  ): ICustomersGroups[] {
    const customersGroups: ICustomersGroups[] = customersGroupsToCheck.filter(isPresent);
    if (customersGroups.length > 0) {
      const customersGroupsCollectionIdentifiers = customersGroupsCollection.map(
        customersGroupsItem => getCustomersGroupsIdentifier(customersGroupsItem)!
      );
      const customersGroupsToAdd = customersGroups.filter(customersGroupsItem => {
        const customersGroupsIdentifier = getCustomersGroupsIdentifier(customersGroupsItem);
        if (customersGroupsIdentifier == null || customersGroupsCollectionIdentifiers.includes(customersGroupsIdentifier)) {
          return false;
        }
        customersGroupsCollectionIdentifiers.push(customersGroupsIdentifier);
        return true;
      });
      return [...customersGroupsToAdd, ...customersGroupsCollection];
    }
    return customersGroupsCollection;
  }
}
