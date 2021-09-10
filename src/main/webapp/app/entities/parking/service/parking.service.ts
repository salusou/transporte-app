import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParking, getParkingIdentifier } from '../parking.model';

export type EntityResponseType = HttpResponse<IParking>;
export type EntityArrayResponseType = HttpResponse<IParking[]>;

@Injectable({ providedIn: 'root' })
export class ParkingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parkings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parking: IParking): Observable<EntityResponseType> {
    return this.http.post<IParking>(this.resourceUrl, parking, { observe: 'response' });
  }

  update(parking: IParking): Observable<EntityResponseType> {
    return this.http.put<IParking>(`${this.resourceUrl}/${getParkingIdentifier(parking) as number}`, parking, { observe: 'response' });
  }

  partialUpdate(parking: IParking): Observable<EntityResponseType> {
    return this.http.patch<IParking>(`${this.resourceUrl}/${getParkingIdentifier(parking) as number}`, parking, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParking>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParking[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParkingToCollectionIfMissing(parkingCollection: IParking[], ...parkingsToCheck: (IParking | null | undefined)[]): IParking[] {
    const parkings: IParking[] = parkingsToCheck.filter(isPresent);
    if (parkings.length > 0) {
      const parkingCollectionIdentifiers = parkingCollection.map(parkingItem => getParkingIdentifier(parkingItem)!);
      const parkingsToAdd = parkings.filter(parkingItem => {
        const parkingIdentifier = getParkingIdentifier(parkingItem);
        if (parkingIdentifier == null || parkingCollectionIdentifiers.includes(parkingIdentifier)) {
          return false;
        }
        parkingCollectionIdentifiers.push(parkingIdentifier);
        return true;
      });
      return [...parkingsToAdd, ...parkingCollection];
    }
    return parkingCollection;
  }
}
