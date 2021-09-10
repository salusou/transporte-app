import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISupplierContacts, getSupplierContactsIdentifier } from '../supplier-contacts.model';

export type EntityResponseType = HttpResponse<ISupplierContacts>;
export type EntityArrayResponseType = HttpResponse<ISupplierContacts[]>;

@Injectable({ providedIn: 'root' })
export class SupplierContactsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/supplier-contacts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(supplierContacts: ISupplierContacts): Observable<EntityResponseType> {
    return this.http.post<ISupplierContacts>(this.resourceUrl, supplierContacts, { observe: 'response' });
  }

  update(supplierContacts: ISupplierContacts): Observable<EntityResponseType> {
    return this.http.put<ISupplierContacts>(
      `${this.resourceUrl}/${getSupplierContactsIdentifier(supplierContacts) as number}`,
      supplierContacts,
      { observe: 'response' }
    );
  }

  partialUpdate(supplierContacts: ISupplierContacts): Observable<EntityResponseType> {
    return this.http.patch<ISupplierContacts>(
      `${this.resourceUrl}/${getSupplierContactsIdentifier(supplierContacts) as number}`,
      supplierContacts,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISupplierContacts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupplierContacts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSupplierContactsToCollectionIfMissing(
    supplierContactsCollection: ISupplierContacts[],
    ...supplierContactsToCheck: (ISupplierContacts | null | undefined)[]
  ): ISupplierContacts[] {
    const supplierContacts: ISupplierContacts[] = supplierContactsToCheck.filter(isPresent);
    if (supplierContacts.length > 0) {
      const supplierContactsCollectionIdentifiers = supplierContactsCollection.map(
        supplierContactsItem => getSupplierContactsIdentifier(supplierContactsItem)!
      );
      const supplierContactsToAdd = supplierContacts.filter(supplierContactsItem => {
        const supplierContactsIdentifier = getSupplierContactsIdentifier(supplierContactsItem);
        if (supplierContactsIdentifier == null || supplierContactsCollectionIdentifiers.includes(supplierContactsIdentifier)) {
          return false;
        }
        supplierContactsCollectionIdentifiers.push(supplierContactsIdentifier);
        return true;
      });
      return [...supplierContactsToAdd, ...supplierContactsCollection];
    }
    return supplierContactsCollection;
  }
}
