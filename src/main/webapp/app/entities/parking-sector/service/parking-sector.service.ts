import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParkingSector, getParkingSectorIdentifier } from '../parking-sector.model';

export type EntityResponseType = HttpResponse<IParkingSector>;
export type EntityArrayResponseType = HttpResponse<IParkingSector[]>;

@Injectable({ providedIn: 'root' })
export class ParkingSectorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parking-sectors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parkingSector: IParkingSector): Observable<EntityResponseType> {
    return this.http.post<IParkingSector>(this.resourceUrl, parkingSector, { observe: 'response' });
  }

  update(parkingSector: IParkingSector): Observable<EntityResponseType> {
    return this.http.put<IParkingSector>(`${this.resourceUrl}/${getParkingSectorIdentifier(parkingSector) as number}`, parkingSector, {
      observe: 'response',
    });
  }

  partialUpdate(parkingSector: IParkingSector): Observable<EntityResponseType> {
    return this.http.patch<IParkingSector>(`${this.resourceUrl}/${getParkingSectorIdentifier(parkingSector) as number}`, parkingSector, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParkingSector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParkingSector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParkingSectorToCollectionIfMissing(
    parkingSectorCollection: IParkingSector[],
    ...parkingSectorsToCheck: (IParkingSector | null | undefined)[]
  ): IParkingSector[] {
    const parkingSectors: IParkingSector[] = parkingSectorsToCheck.filter(isPresent);
    if (parkingSectors.length > 0) {
      const parkingSectorCollectionIdentifiers = parkingSectorCollection.map(
        parkingSectorItem => getParkingSectorIdentifier(parkingSectorItem)!
      );
      const parkingSectorsToAdd = parkingSectors.filter(parkingSectorItem => {
        const parkingSectorIdentifier = getParkingSectorIdentifier(parkingSectorItem);
        if (parkingSectorIdentifier == null || parkingSectorCollectionIdentifiers.includes(parkingSectorIdentifier)) {
          return false;
        }
        parkingSectorCollectionIdentifiers.push(parkingSectorIdentifier);
        return true;
      });
      return [...parkingSectorsToAdd, ...parkingSectorCollection];
    }
    return parkingSectorCollection;
  }
}
