import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICities, getCitiesIdentifier } from '../cities.model';

export type EntityResponseType = HttpResponse<ICities>;
export type EntityArrayResponseType = HttpResponse<ICities[]>;

@Injectable({ providedIn: 'root' })
export class CitiesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cities: ICities): Observable<EntityResponseType> {
    return this.http.post<ICities>(this.resourceUrl, cities, { observe: 'response' });
  }

  update(cities: ICities): Observable<EntityResponseType> {
    return this.http.put<ICities>(`${this.resourceUrl}/${getCitiesIdentifier(cities) as number}`, cities, { observe: 'response' });
  }

  partialUpdate(cities: ICities): Observable<EntityResponseType> {
    return this.http.patch<ICities>(`${this.resourceUrl}/${getCitiesIdentifier(cities) as number}`, cities, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICities>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICities[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCitiesToCollectionIfMissing(citiesCollection: ICities[], ...citiesToCheck: (ICities | null | undefined)[]): ICities[] {
    const cities: ICities[] = citiesToCheck.filter(isPresent);
    if (cities.length > 0) {
      const citiesCollectionIdentifiers = citiesCollection.map(citiesItem => getCitiesIdentifier(citiesItem)!);
      const citiesToAdd = cities.filter(citiesItem => {
        const citiesIdentifier = getCitiesIdentifier(citiesItem);
        if (citiesIdentifier == null || citiesCollectionIdentifiers.includes(citiesIdentifier)) {
          return false;
        }
        citiesCollectionIdentifiers.push(citiesIdentifier);
        return true;
      });
      return [...citiesToAdd, ...citiesCollection];
    }
    return citiesCollection;
  }
}
