import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomersContacts, getCustomersContactsIdentifier } from '../customers-contacts.model';

export type EntityResponseType = HttpResponse<ICustomersContacts>;
export type EntityArrayResponseType = HttpResponse<ICustomersContacts[]>;

@Injectable({ providedIn: 'root' })
export class CustomersContactsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customers-contacts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customersContacts: ICustomersContacts): Observable<EntityResponseType> {
    return this.http.post<ICustomersContacts>(this.resourceUrl, customersContacts, { observe: 'response' });
  }

  update(customersContacts: ICustomersContacts): Observable<EntityResponseType> {
    return this.http.put<ICustomersContacts>(
      `${this.resourceUrl}/${getCustomersContactsIdentifier(customersContacts) as number}`,
      customersContacts,
      { observe: 'response' }
    );
  }

  partialUpdate(customersContacts: ICustomersContacts): Observable<EntityResponseType> {
    return this.http.patch<ICustomersContacts>(
      `${this.resourceUrl}/${getCustomersContactsIdentifier(customersContacts) as number}`,
      customersContacts,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomersContacts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomersContacts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomersContactsToCollectionIfMissing(
    customersContactsCollection: ICustomersContacts[],
    ...customersContactsToCheck: (ICustomersContacts | null | undefined)[]
  ): ICustomersContacts[] {
    const customersContacts: ICustomersContacts[] = customersContactsToCheck.filter(isPresent);
    if (customersContacts.length > 0) {
      const customersContactsCollectionIdentifiers = customersContactsCollection.map(
        customersContactsItem => getCustomersContactsIdentifier(customersContactsItem)!
      );
      const customersContactsToAdd = customersContacts.filter(customersContactsItem => {
        const customersContactsIdentifier = getCustomersContactsIdentifier(customersContactsItem);
        if (customersContactsIdentifier == null || customersContactsCollectionIdentifiers.includes(customersContactsIdentifier)) {
          return false;
        }
        customersContactsCollectionIdentifiers.push(customersContactsIdentifier);
        return true;
      });
      return [...customersContactsToAdd, ...customersContactsCollection];
    }
    return customersContactsCollection;
  }
}
