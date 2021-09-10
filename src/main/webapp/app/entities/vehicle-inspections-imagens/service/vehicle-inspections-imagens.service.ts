import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleInspectionsImagens, getVehicleInspectionsImagensIdentifier } from '../vehicle-inspections-imagens.model';

export type EntityResponseType = HttpResponse<IVehicleInspectionsImagens>;
export type EntityArrayResponseType = HttpResponse<IVehicleInspectionsImagens[]>;

@Injectable({ providedIn: 'root' })
export class VehicleInspectionsImagensService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-inspections-imagens');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleInspectionsImagens: IVehicleInspectionsImagens): Observable<EntityResponseType> {
    return this.http.post<IVehicleInspectionsImagens>(this.resourceUrl, vehicleInspectionsImagens, { observe: 'response' });
  }

  update(vehicleInspectionsImagens: IVehicleInspectionsImagens): Observable<EntityResponseType> {
    return this.http.put<IVehicleInspectionsImagens>(
      `${this.resourceUrl}/${getVehicleInspectionsImagensIdentifier(vehicleInspectionsImagens) as number}`,
      vehicleInspectionsImagens,
      { observe: 'response' }
    );
  }

  partialUpdate(vehicleInspectionsImagens: IVehicleInspectionsImagens): Observable<EntityResponseType> {
    return this.http.patch<IVehicleInspectionsImagens>(
      `${this.resourceUrl}/${getVehicleInspectionsImagensIdentifier(vehicleInspectionsImagens) as number}`,
      vehicleInspectionsImagens,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleInspectionsImagens>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicleInspectionsImagens[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleInspectionsImagensToCollectionIfMissing(
    vehicleInspectionsImagensCollection: IVehicleInspectionsImagens[],
    ...vehicleInspectionsImagensToCheck: (IVehicleInspectionsImagens | null | undefined)[]
  ): IVehicleInspectionsImagens[] {
    const vehicleInspectionsImagens: IVehicleInspectionsImagens[] = vehicleInspectionsImagensToCheck.filter(isPresent);
    if (vehicleInspectionsImagens.length > 0) {
      const vehicleInspectionsImagensCollectionIdentifiers = vehicleInspectionsImagensCollection.map(
        vehicleInspectionsImagensItem => getVehicleInspectionsImagensIdentifier(vehicleInspectionsImagensItem)!
      );
      const vehicleInspectionsImagensToAdd = vehicleInspectionsImagens.filter(vehicleInspectionsImagensItem => {
        const vehicleInspectionsImagensIdentifier = getVehicleInspectionsImagensIdentifier(vehicleInspectionsImagensItem);
        if (
          vehicleInspectionsImagensIdentifier == null ||
          vehicleInspectionsImagensCollectionIdentifiers.includes(vehicleInspectionsImagensIdentifier)
        ) {
          return false;
        }
        vehicleInspectionsImagensCollectionIdentifiers.push(vehicleInspectionsImagensIdentifier);
        return true;
      });
      return [...vehicleInspectionsImagensToAdd, ...vehicleInspectionsImagensCollection];
    }
    return vehicleInspectionsImagensCollection;
  }
}
