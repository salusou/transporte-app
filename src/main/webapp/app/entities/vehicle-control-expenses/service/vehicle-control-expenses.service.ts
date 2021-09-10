import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleControlExpenses, getVehicleControlExpensesIdentifier } from '../vehicle-control-expenses.model';

export type EntityResponseType = HttpResponse<IVehicleControlExpenses>;
export type EntityArrayResponseType = HttpResponse<IVehicleControlExpenses[]>;

@Injectable({ providedIn: 'root' })
export class VehicleControlExpensesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-control-expenses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleControlExpenses: IVehicleControlExpenses): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlExpenses);
    return this.http
      .post<IVehicleControlExpenses>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleControlExpenses: IVehicleControlExpenses): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlExpenses);
    return this.http
      .put<IVehicleControlExpenses>(`${this.resourceUrl}/${getVehicleControlExpensesIdentifier(vehicleControlExpenses) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(vehicleControlExpenses: IVehicleControlExpenses): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleControlExpenses);
    return this.http
      .patch<IVehicleControlExpenses>(
        `${this.resourceUrl}/${getVehicleControlExpensesIdentifier(vehicleControlExpenses) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleControlExpenses>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleControlExpenses[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleControlExpensesToCollectionIfMissing(
    vehicleControlExpensesCollection: IVehicleControlExpenses[],
    ...vehicleControlExpensesToCheck: (IVehicleControlExpenses | null | undefined)[]
  ): IVehicleControlExpenses[] {
    const vehicleControlExpenses: IVehicleControlExpenses[] = vehicleControlExpensesToCheck.filter(isPresent);
    if (vehicleControlExpenses.length > 0) {
      const vehicleControlExpensesCollectionIdentifiers = vehicleControlExpensesCollection.map(
        vehicleControlExpensesItem => getVehicleControlExpensesIdentifier(vehicleControlExpensesItem)!
      );
      const vehicleControlExpensesToAdd = vehicleControlExpenses.filter(vehicleControlExpensesItem => {
        const vehicleControlExpensesIdentifier = getVehicleControlExpensesIdentifier(vehicleControlExpensesItem);
        if (
          vehicleControlExpensesIdentifier == null ||
          vehicleControlExpensesCollectionIdentifiers.includes(vehicleControlExpensesIdentifier)
        ) {
          return false;
        }
        vehicleControlExpensesCollectionIdentifiers.push(vehicleControlExpensesIdentifier);
        return true;
      });
      return [...vehicleControlExpensesToAdd, ...vehicleControlExpensesCollection];
    }
    return vehicleControlExpensesCollection;
  }

  protected convertDateFromClient(vehicleControlExpenses: IVehicleControlExpenses): IVehicleControlExpenses {
    return Object.assign({}, vehicleControlExpenses, {
      vehicleControlExpensesDueDate: vehicleControlExpenses.vehicleControlExpensesDueDate?.isValid()
        ? vehicleControlExpenses.vehicleControlExpensesDueDate.format(DATE_FORMAT)
        : undefined,
      vehicleControlExpensesPaymentDate: vehicleControlExpenses.vehicleControlExpensesPaymentDate?.isValid()
        ? vehicleControlExpenses.vehicleControlExpensesPaymentDate.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vehicleControlExpensesDueDate = res.body.vehicleControlExpensesDueDate
        ? dayjs(res.body.vehicleControlExpensesDueDate)
        : undefined;
      res.body.vehicleControlExpensesPaymentDate = res.body.vehicleControlExpensesPaymentDate
        ? dayjs(res.body.vehicleControlExpensesPaymentDate)
        : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleControlExpenses: IVehicleControlExpenses) => {
        vehicleControlExpenses.vehicleControlExpensesDueDate = vehicleControlExpenses.vehicleControlExpensesDueDate
          ? dayjs(vehicleControlExpenses.vehicleControlExpensesDueDate)
          : undefined;
        vehicleControlExpenses.vehicleControlExpensesPaymentDate = vehicleControlExpenses.vehicleControlExpensesPaymentDate
          ? dayjs(vehicleControlExpenses.vehicleControlExpensesPaymentDate)
          : undefined;
      });
    }
    return res;
  }
}
