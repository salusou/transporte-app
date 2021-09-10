import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomers, getCustomersIdentifier } from '../customers.model';

export type EntityResponseType = HttpResponse<ICustomers>;
export type EntityArrayResponseType = HttpResponse<ICustomers[]>;

@Injectable({ providedIn: 'root' })
export class CustomersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customers: ICustomers): Observable<EntityResponseType> {
    return this.http.post<ICustomers>(this.resourceUrl, customers, { observe: 'response' });
  }

  update(customers: ICustomers): Observable<EntityResponseType> {
    return this.http.put<ICustomers>(`${this.resourceUrl}/${getCustomersIdentifier(customers) as number}`, customers, {
      observe: 'response',
    });
  }

  partialUpdate(customers: ICustomers): Observable<EntityResponseType> {
    return this.http.patch<ICustomers>(`${this.resourceUrl}/${getCustomersIdentifier(customers) as number}`, customers, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomersToCollectionIfMissing(
    customersCollection: ICustomers[],
    ...customersToCheck: (ICustomers | null | undefined)[]
  ): ICustomers[] {
    const customers: ICustomers[] = customersToCheck.filter(isPresent);
    if (customers.length > 0) {
      const customersCollectionIdentifiers = customersCollection.map(customersItem => getCustomersIdentifier(customersItem)!);
      const customersToAdd = customers.filter(customersItem => {
        const customersIdentifier = getCustomersIdentifier(customersItem);
        if (customersIdentifier == null || customersCollectionIdentifiers.includes(customersIdentifier)) {
          return false;
        }
        customersCollectionIdentifiers.push(customersIdentifier);
        return true;
      });
      return [...customersToAdd, ...customersCollection];
    }
    return customersCollection;
  }
}
