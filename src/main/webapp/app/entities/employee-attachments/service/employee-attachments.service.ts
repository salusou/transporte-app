import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployeeAttachments, getEmployeeAttachmentsIdentifier } from '../employee-attachments.model';

export type EntityResponseType = HttpResponse<IEmployeeAttachments>;
export type EntityArrayResponseType = HttpResponse<IEmployeeAttachments[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeAttachmentsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employee-attachments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employeeAttachments: IEmployeeAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeAttachments);
    return this.http
      .post<IEmployeeAttachments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeeAttachments: IEmployeeAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeAttachments);
    return this.http
      .put<IEmployeeAttachments>(`${this.resourceUrl}/${getEmployeeAttachmentsIdentifier(employeeAttachments) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(employeeAttachments: IEmployeeAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeAttachments);
    return this.http
      .patch<IEmployeeAttachments>(`${this.resourceUrl}/${getEmployeeAttachmentsIdentifier(employeeAttachments) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeeAttachments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeeAttachments[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmployeeAttachmentsToCollectionIfMissing(
    employeeAttachmentsCollection: IEmployeeAttachments[],
    ...employeeAttachmentsToCheck: (IEmployeeAttachments | null | undefined)[]
  ): IEmployeeAttachments[] {
    const employeeAttachments: IEmployeeAttachments[] = employeeAttachmentsToCheck.filter(isPresent);
    if (employeeAttachments.length > 0) {
      const employeeAttachmentsCollectionIdentifiers = employeeAttachmentsCollection.map(
        employeeAttachmentsItem => getEmployeeAttachmentsIdentifier(employeeAttachmentsItem)!
      );
      const employeeAttachmentsToAdd = employeeAttachments.filter(employeeAttachmentsItem => {
        const employeeAttachmentsIdentifier = getEmployeeAttachmentsIdentifier(employeeAttachmentsItem);
        if (employeeAttachmentsIdentifier == null || employeeAttachmentsCollectionIdentifiers.includes(employeeAttachmentsIdentifier)) {
          return false;
        }
        employeeAttachmentsCollectionIdentifiers.push(employeeAttachmentsIdentifier);
        return true;
      });
      return [...employeeAttachmentsToAdd, ...employeeAttachmentsCollection];
    }
    return employeeAttachmentsCollection;
  }

  protected convertDateFromClient(employeeAttachments: IEmployeeAttachments): IEmployeeAttachments {
    return Object.assign({}, employeeAttachments, {
      attachedDate: employeeAttachments.attachedDate?.isValid() ? employeeAttachments.attachedDate.toJSON() : undefined,
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
      res.body.forEach((employeeAttachments: IEmployeeAttachments) => {
        employeeAttachments.attachedDate = employeeAttachments.attachedDate ? dayjs(employeeAttachments.attachedDate) : undefined;
      });
    }
    return res;
  }
}
