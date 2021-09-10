import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleControlAttachments, getVehicleControlAttachmentsIdentifier } from '../vehicle-control-attachments.model';

export type EntityResponseType = HttpResponse<IVehicleControlAttachments>;
export type EntityArrayResponseType = HttpResponse<IVehicleControlAttachments[]>;

@Injectable({ providedIn: 'root' })
export class VehicleControlAttachmentsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-control-attachments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleControlAttachments: IVehicleControlAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlAttachments);
    return this.http
      .post<IVehicleControlAttachments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleControlAttachments: IVehicleControlAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlAttachments);
    return this.http
      .put<IVehicleControlAttachments>(
        `${this.resourceUrl}/${getVehicleControlAttachmentsIdentifier(vehicleControlAttachments) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleControlAttachments: IVehicleControlAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlAttachments);
    return this.http
      .patch<IVehicleControlAttachments>(
        `${this.resourceUrl}/${getVehicleControlAttachmentsIdentifier(vehicleControlAttachments) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleControlAttachments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleControlAttachments[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleControlAttachmentsToCollectionIfMissing(
    vehicleControlAttachmentsCollection: IVehicleControlAttachments[],
    ...vehicleControlAttachmentsToCheck: (IVehicleControlAttachments | null | undefined)[]
  ): IVehicleControlAttachments[] {
    const vehicleControlAttachments: IVehicleControlAttachments[] = vehicleControlAttachmentsToCheck.filter(isPresent);
    if (vehicleControlAttachments.length > 0) {
      const vehicleControlAttachmentsCollectionIdentifiers = vehicleControlAttachmentsCollection.map(
        vehicleControlAttachmentsItem => getVehicleControlAttachmentsIdentifier(vehicleControlAttachmentsItem)!
      );
      const vehicleControlAttachmentsToAdd = vehicleControlAttachments.filter(vehicleControlAttachmentsItem => {
        const vehicleControlAttachmentsIdentifier = getVehicleControlAttachmentsIdentifier(vehicleControlAttachmentsItem);
        if (
          vehicleControlAttachmentsIdentifier == null ||
          vehicleControlAttachmentsCollectionIdentifiers.includes(vehicleControlAttachmentsIdentifier)
        ) {
          return false;
        }
        vehicleControlAttachmentsCollectionIdentifiers.push(vehicleControlAttachmentsIdentifier);
        return true;
      });
      return [...vehicleControlAttachmentsToAdd, ...vehicleControlAttachmentsCollection];
    }
    return vehicleControlAttachmentsCollection;
  }

  protected convertDateFromClient(vehicleControlAttachments: IVehicleControlAttachments): IVehicleControlAttachments {
    return Object.assign({}, vehicleControlAttachments, {
      attachedDate: vehicleControlAttachments.attachedDate?.isValid() ? vehicleControlAttachments.attachedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.attachedDate = res.body.attachedDate ? dayjs(res.body.attachedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleControlAttachments: IVehicleControlAttachments) => {
        vehicleControlAttachments.attachedDate = vehicleControlAttachments.attachedDate
          ? dayjs(vehicleControlAttachments.attachedDate)
          : undefined;
      });
    }
    return res;
  }
}
