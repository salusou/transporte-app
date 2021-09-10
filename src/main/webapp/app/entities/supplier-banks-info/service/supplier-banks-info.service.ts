import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISupplierBanksInfo, getSupplierBanksInfoIdentifier } from '../supplier-banks-info.model';

export type EntityResponseType = HttpResponse<ISupplierBanksInfo>;
export type EntityArrayResponseType = HttpResponse<ISupplierBanksInfo[]>;

@Injectable({ providedIn: 'root' })
export class SupplierBanksInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/supplier-banks-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(supplierBanksInfo: ISupplierBanksInfo): Observable<EntityResponseType> {
    return this.http.post<ISupplierBanksInfo>(this.resourceUrl, supplierBanksInfo, { observe: 'response' });
  }

  update(supplierBanksInfo: ISupplierBanksInfo): Observable<EntityResponseType> {
    return this.http.put<ISupplierBanksInfo>(
      `${this.resourceUrl}/${getSupplierBanksInfoIdentifier(supplierBanksInfo) as number}`,
      supplierBanksInfo,
      { observe: 'response' }
    );
  }

  partialUpdate(supplierBanksInfo: ISupplierBanksInfo): Observable<EntityResponseType> {
    return this.http.patch<ISupplierBanksInfo>(
      `${this.resourceUrl}/${getSupplierBanksInfoIdentifier(supplierBanksInfo) as number}`,
      supplierBanksInfo,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISupplierBanksInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupplierBanksInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSupplierBanksInfoToCollectionIfMissing(
    supplierBanksInfoCollection: ISupplierBanksInfo[],
    ...supplierBanksInfosToCheck: (ISupplierBanksInfo | null | undefined)[]
  ): ISupplierBanksInfo[] {
    const supplierBanksInfos: ISupplierBanksInfo[] = supplierBanksInfosToCheck.filter(isPresent);
    if (supplierBanksInfos.length > 0) {
      const supplierBanksInfoCollectionIdentifiers = supplierBanksInfoCollection.map(
        supplierBanksInfoItem => getSupplierBanksInfoIdentifier(supplierBanksInfoItem)!
      );
      const supplierBanksInfosToAdd = supplierBanksInfos.filter(supplierBanksInfoItem => {
        const supplierBanksInfoIdentifier = getSupplierBanksInfoIdentifier(supplierBanksInfoItem);
        if (supplierBanksInfoIdentifier == null || supplierBanksInfoCollectionIdentifiers.includes(supplierBanksInfoIdentifier)) {
          return false;
        }
        supplierBanksInfoCollectionIdentifiers.push(supplierBanksInfoIdentifier);
        return true;
      });
      return [...supplierBanksInfosToAdd, ...supplierBanksInfoCollection];
    }
    return supplierBanksInfoCollection;
  }
}
