import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHousingVehicleItem, getHousingVehicleItemIdentifier } from '../housing-vehicle-item.model';

export type EntityResponseType = HttpResponse<IHousingVehicleItem>;
export type EntityArrayResponseType = HttpResponse<IHousingVehicleItem[]>;

@Injectable({ providedIn: 'root' })
export class HousingVehicleItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/housing-vehicle-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(housingVehicleItem: IHousingVehicleItem): Observable<EntityResponseType> {
    return this.http.post<IHousingVehicleItem>(this.resourceUrl, housingVehicleItem, { observe: 'response' });
  }

  update(housingVehicleItem: IHousingVehicleItem): Observable<EntityResponseType> {
    return this.http.put<IHousingVehicleItem>(
      `${this.resourceUrl}/${getHousingVehicleItemIdentifier(housingVehicleItem) as number}`,
      housingVehicleItem,
      { observe: 'response' }
    );
  }

  partialUpdate(housingVehicleItem: IHousingVehicleItem): Observable<EntityResponseType> {
    return this.http.patch<IHousingVehicleItem>(
      `${this.resourceUrl}/${getHousingVehicleItemIdentifier(housingVehicleItem) as number}`,
      housingVehicleItem,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHousingVehicleItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHousingVehicleItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHousingVehicleItemToCollectionIfMissing(
    housingVehicleItemCollection: IHousingVehicleItem[],
    ...housingVehicleItemsToCheck: (IHousingVehicleItem | null | undefined)[]
  ): IHousingVehicleItem[] {
    const housingVehicleItems: IHousingVehicleItem[] = housingVehicleItemsToCheck.filter(isPresent);
    if (housingVehicleItems.length > 0) {
      const housingVehicleItemCollectionIdentifiers = housingVehicleItemCollection.map(
        housingVehicleItemItem => getHousingVehicleItemIdentifier(housingVehicleItemItem)!
      );
      const housingVehicleItemsToAdd = housingVehicleItems.filter(housingVehicleItemItem => {
        const housingVehicleItemIdentifier = getHousingVehicleItemIdentifier(housingVehicleItemItem);
        if (housingVehicleItemIdentifier == null || housingVehicleItemCollectionIdentifiers.includes(housingVehicleItemIdentifier)) {
          return false;
        }
        housingVehicleItemCollectionIdentifiers.push(housingVehicleItemIdentifier);
        return true;
      });
      return [...housingVehicleItemsToAdd, ...housingVehicleItemCollection];
    }
    return housingVehicleItemCollection;
  }
}
