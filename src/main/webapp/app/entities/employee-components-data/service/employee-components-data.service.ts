import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployeeComponentsData, getEmployeeComponentsDataIdentifier } from '../employee-components-data.model';

export type EntityResponseType = HttpResponse<IEmployeeComponentsData>;
export type EntityArrayResponseType = HttpResponse<IEmployeeComponentsData[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeComponentsDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employee-components-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employeeComponentsData: IEmployeeComponentsData): Observable<EntityResponseType> {
    return this.http.post<IEmployeeComponentsData>(this.resourceUrl, employeeComponentsData, { observe: 'response' });
  }

  update(employeeComponentsData: IEmployeeComponentsData): Observable<EntityResponseType> {
    return this.http.put<IEmployeeComponentsData>(
      `${this.resourceUrl}/${getEmployeeComponentsDataIdentifier(employeeComponentsData) as number}`,
      employeeComponentsData,
      { observe: 'response' }
    );
  }

  partialUpdate(employeeComponentsData: IEmployeeComponentsData): Observable<EntityResponseType> {
    return this.http.patch<IEmployeeComponentsData>(
      `${this.resourceUrl}/${getEmployeeComponentsDataIdentifier(employeeComponentsData) as number}`,
      employeeComponentsData,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeComponentsData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeComponentsData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmployeeComponentsDataToCollectionIfMissing(
    employeeComponentsDataCollection: IEmployeeComponentsData[],
    ...employeeComponentsDataToCheck: (IEmployeeComponentsData | null | undefined)[]
  ): IEmployeeComponentsData[] {
    const employeeComponentsData: IEmployeeComponentsData[] = employeeComponentsDataToCheck.filter(isPresent);
    if (employeeComponentsData.length > 0) {
      const employeeComponentsDataCollectionIdentifiers = employeeComponentsDataCollection.map(
        employeeComponentsDataItem => getEmployeeComponentsDataIdentifier(employeeComponentsDataItem)!
      );
      const employeeComponentsDataToAdd = employeeComponentsData.filter(employeeComponentsDataItem => {
        const employeeComponentsDataIdentifier = getEmployeeComponentsDataIdentifier(employeeComponentsDataItem);
        if (
          employeeComponentsDataIdentifier == null ||
          employeeComponentsDataCollectionIdentifiers.includes(employeeComponentsDataIdentifier)
        ) {
          return false;
        }
        employeeComponentsDataCollectionIdentifiers.push(employeeComponentsDataIdentifier);
        return true;
      });
      return [...employeeComponentsDataToAdd, ...employeeComponentsDataCollection];
    }
    return employeeComponentsDataCollection;
  }
}
