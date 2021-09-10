import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployees, getEmployeesIdentifier } from '../employees.model';

export type EntityResponseType = HttpResponse<IEmployees>;
export type EntityArrayResponseType = HttpResponse<IEmployees[]>;

@Injectable({ providedIn: 'root' })
export class EmployeesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employees: IEmployees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employees);
    return this.http
      .post<IEmployees>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employees: IEmployees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employees);
    return this.http
      .put<IEmployees>(`${this.resourceUrl}/${getEmployeesIdentifier(employees) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(employees: IEmployees): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employees);
    return this.http
      .patch<IEmployees>(`${this.resourceUrl}/${getEmployeesIdentifier(employees) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployees>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployees[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmployeesToCollectionIfMissing(
    employeesCollection: IEmployees[],
    ...employeesToCheck: (IEmployees | null | undefined)[]
  ): IEmployees[] {
    const employees: IEmployees[] = employeesToCheck.filter(isPresent);
    if (employees.length > 0) {
      const employeesCollectionIdentifiers = employeesCollection.map(employeesItem => getEmployeesIdentifier(employeesItem)!);
      const employeesToAdd = employees.filter(employeesItem => {
        const employeesIdentifier = getEmployeesIdentifier(employeesItem);
        if (employeesIdentifier == null || employeesCollectionIdentifiers.includes(employeesIdentifier)) {
          return false;
        }
        employeesCollectionIdentifiers.push(employeesIdentifier);
        return true;
      });
      return [...employeesToAdd, ...employeesCollection];
    }
    return employeesCollection;
  }

  protected convertDateFromClient(employees: IEmployees): IEmployees {
    return Object.assign({}, employees, {
      employeeBirthday: employees.employeeBirthday?.isValid() ? employees.employeeBirthday.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.employeeBirthday = res.body.employeeBirthday ? dayjs(res.body.employeeBirthday) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employees: IEmployees) => {
        employees.employeeBirthday = employees.employeeBirthday ? dayjs(employees.employeeBirthday) : undefined;
      });
    }
    return res;
  }
}
