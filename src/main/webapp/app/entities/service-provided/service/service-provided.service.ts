import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServiceProvided, getServiceProvidedIdentifier } from '../service-provided.model';

export type EntityResponseType = HttpResponse<IServiceProvided>;
export type EntityArrayResponseType = HttpResponse<IServiceProvided[]>;

@Injectable({ providedIn: 'root' })
export class ServiceProvidedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/service-provideds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(serviceProvided: IServiceProvided): Observable<EntityResponseType> {
    return this.http.post<IServiceProvided>(this.resourceUrl, serviceProvided, { observe: 'response' });
  }

  update(serviceProvided: IServiceProvided): Observable<EntityResponseType> {
    return this.http.put<IServiceProvided>(
      `${this.resourceUrl}/${getServiceProvidedIdentifier(serviceProvided) as number}`,
      serviceProvided,
      { observe: 'response' }
    );
  }

  partialUpdate(serviceProvided: IServiceProvided): Observable<EntityResponseType> {
    return this.http.patch<IServiceProvided>(
      `${this.resourceUrl}/${getServiceProvidedIdentifier(serviceProvided) as number}`,
      serviceProvided,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceProvided>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceProvided[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addServiceProvidedToCollectionIfMissing(
    serviceProvidedCollection: IServiceProvided[],
    ...serviceProvidedsToCheck: (IServiceProvided | null | undefined)[]
  ): IServiceProvided[] {
    const serviceProvideds: IServiceProvided[] = serviceProvidedsToCheck.filter(isPresent);
    if (serviceProvideds.length > 0) {
      const serviceProvidedCollectionIdentifiers = serviceProvidedCollection.map(
        serviceProvidedItem => getServiceProvidedIdentifier(serviceProvidedItem)!
      );
      const serviceProvidedsToAdd = serviceProvideds.filter(serviceProvidedItem => {
        const serviceProvidedIdentifier = getServiceProvidedIdentifier(serviceProvidedItem);
        if (serviceProvidedIdentifier == null || serviceProvidedCollectionIdentifiers.includes(serviceProvidedIdentifier)) {
          return false;
        }
        serviceProvidedCollectionIdentifiers.push(serviceProvidedIdentifier);
        return true;
      });
      return [...serviceProvidedsToAdd, ...serviceProvidedCollection];
    }
    return serviceProvidedCollection;
  }
}
