import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISuppliers, getSuppliersIdentifier } from '../suppliers.model';

export type EntityResponseType = HttpResponse<ISuppliers>;
export type EntityArrayResponseType = HttpResponse<ISuppliers[]>;

@Injectable({ providedIn: 'root' })
export class SuppliersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/suppliers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(suppliers: ISuppliers): Observable<EntityResponseType> {
    return this.http.post<ISuppliers>(this.resourceUrl, suppliers, { observe: 'response' });
  }

  update(suppliers: ISuppliers): Observable<EntityResponseType> {
    return this.http.put<ISuppliers>(`${this.resourceUrl}/${getSuppliersIdentifier(suppliers) as number}`, suppliers, {
      observe: 'response',
    });
  }

  partialUpdate(suppliers: ISuppliers): Observable<EntityResponseType> {
    return this.http.patch<ISuppliers>(`${this.resourceUrl}/${getSuppliersIdentifier(suppliers) as number}`, suppliers, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISuppliers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuppliers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSuppliersToCollectionIfMissing(
    suppliersCollection: ISuppliers[],
    ...suppliersToCheck: (ISuppliers | null | undefined)[]
  ): ISuppliers[] {
    const suppliers: ISuppliers[] = suppliersToCheck.filter(isPresent);
    if (suppliers.length > 0) {
      const suppliersCollectionIdentifiers = suppliersCollection.map(suppliersItem => getSuppliersIdentifier(suppliersItem)!);
      const suppliersToAdd = suppliers.filter(suppliersItem => {
        const suppliersIdentifier = getSuppliersIdentifier(suppliersItem);
        if (suppliersIdentifier == null || suppliersCollectionIdentifiers.includes(suppliersIdentifier)) {
          return false;
        }
        suppliersCollectionIdentifiers.push(suppliersIdentifier);
        return true;
      });
      return [...suppliersToAdd, ...suppliersCollection];
    }
    return suppliersCollection;
  }
}
