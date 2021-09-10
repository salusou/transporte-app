import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleControlBilling, getVehicleControlBillingIdentifier } from '../vehicle-control-billing.model';

export type EntityResponseType = HttpResponse<IVehicleControlBilling>;
export type EntityArrayResponseType = HttpResponse<IVehicleControlBilling[]>;

@Injectable({ providedIn: 'root' })
export class VehicleControlBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-control-billings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleControlBilling: IVehicleControlBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlBilling);
    return this.http
      .post<IVehicleControlBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleControlBilling: IVehicleControlBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlBilling);
    return this.http
      .put<IVehicleControlBilling>(`${this.resourceUrl}/${getVehicleControlBillingIdentifier(vehicleControlBilling) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleControlBilling: IVehicleControlBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlBilling);
    return this.http
      .patch<IVehicleControlBilling>(`${this.resourceUrl}/${getVehicleControlBillingIdentifier(vehicleControlBilling) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleControlBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleControlBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleControlBillingToCollectionIfMissing(
    vehicleControlBillingCollection: IVehicleControlBilling[],
    ...vehicleControlBillingsToCheck: (IVehicleControlBilling | null | undefined)[]
  ): IVehicleControlBilling[] {
    const vehicleControlBillings: IVehicleControlBilling[] = vehicleControlBillingsToCheck.filter(isPresent);
    if (vehicleControlBillings.length > 0) {
      const vehicleControlBillingCollectionIdentifiers = vehicleControlBillingCollection.map(
        vehicleControlBillingItem => getVehicleControlBillingIdentifier(vehicleControlBillingItem)!
      );
      const vehicleControlBillingsToAdd = vehicleControlBillings.filter(vehicleControlBillingItem => {
        const vehicleControlBillingIdentifier = getVehicleControlBillingIdentifier(vehicleControlBillingItem);
        if (
          vehicleControlBillingIdentifier == null ||
          vehicleControlBillingCollectionIdentifiers.includes(vehicleControlBillingIdentifier)
        ) {
          return false;
        }
        vehicleControlBillingCollectionIdentifiers.push(vehicleControlBillingIdentifier);
        return true;
      });
      return [...vehicleControlBillingsToAdd, ...vehicleControlBillingCollection];
    }
    return vehicleControlBillingCollection;
  }

  protected convertDateFromClient(vehicleControlBilling: IVehicleControlBilling): IVehicleControlBilling {
    return Object.assign({}, vehicleControlBilling, {
      vehicleControlBillingDate: vehicleControlBilling.vehicleControlBillingDate?.isValid()
        ? vehicleControlBilling.vehicleControlBillingDate.format(DATE_FORMAT)
        : undefined,
      vehicleControlBillingExpirationDate: vehicleControlBilling.vehicleControlBillingExpirationDate?.isValid()
        ? vehicleControlBilling.vehicleControlBillingExpirationDate.format(DATE_FORMAT)
        : undefined,
      vehicleControlBillingPaymentDate: vehicleControlBilling.vehicleControlBillingPaymentDate?.isValid()
        ? vehicleControlBilling.vehicleControlBillingPaymentDate.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleControlBillingDate = res.body.vehicleControlBillingDate ? dayjs(res.body.vehicleControlBillingDate) : undefined;
      res.body.vehicleControlBillingExpirationDate = res.body.vehicleControlBillingExpirationDate
        ? dayjs(res.body.vehicleControlBillingExpirationDate)
        : undefined;
      res.body.vehicleControlBillingPaymentDate = res.body.vehicleControlBillingPaymentDate
        ? dayjs(res.body.vehicleControlBillingPaymentDate)
        : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleControlBilling: IVehicleControlBilling) => {
        vehicleControlBilling.vehicleControlBillingDate = vehicleControlBilling.vehicleControlBillingDate
          ? dayjs(vehicleControlBilling.vehicleControlBillingDate)
          : undefined;
        vehicleControlBilling.vehicleControlBillingExpirationDate = vehicleControlBilling.vehicleControlBillingExpirationDate
          ? dayjs(vehicleControlBilling.vehicleControlBillingExpirationDate)
          : undefined;
        vehicleControlBilling.vehicleControlBillingPaymentDate = vehicleControlBilling.vehicleControlBillingPaymentDate
          ? dayjs(vehicleControlBilling.vehicleControlBillingPaymentDate)
          : undefined;
      });
    }
    return res;
  }
}
