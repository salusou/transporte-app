import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomerAttachments, getCustomerAttachmentsIdentifier } from '../customer-attachments.model';

export type EntityResponseType = HttpResponse<ICustomerAttachments>;
export type EntityArrayResponseType = HttpResponse<ICustomerAttachments[]>;

@Injectable({ providedIn: 'root' })
export class CustomerAttachmentsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customer-attachments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customerAttachments: ICustomerAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerAttachments);
    return this.http
      .post<ICustomerAttachments>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerAttachments: ICustomerAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerAttachments);
    return this.http
      .put<ICustomerAttachments>(`${this.resourceUrl}/${getCustomerAttachmentsIdentifier(customerAttachments) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(customerAttachments: ICustomerAttachments): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerAttachments);
    return this.http
      .patch<ICustomerAttachments>(`${this.resourceUrl}/${getCustomerAttachmentsIdentifier(customerAttachments) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerAttachments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerAttachments[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomerAttachmentsToCollectionIfMissing(
    customerAttachmentsCollection: ICustomerAttachments[],
    ...customerAttachmentsToCheck: (ICustomerAttachments | null | undefined)[]
  ): ICustomerAttachments[] {
    const customerAttachments: ICustomerAttachments[] = customerAttachmentsToCheck.filter(isPresent);
    if (customerAttachments.length > 0) {
      const customerAttachmentsCollectionIdentifiers = customerAttachmentsCollection.map(
        customerAttachmentsItem => getCustomerAttachmentsIdentifier(customerAttachmentsItem)!
      );
      const customerAttachmentsToAdd = customerAttachments.filter(customerAttachmentsItem => {
        const customerAttachmentsIdentifier = getCustomerAttachmentsIdentifier(customerAttachmentsItem);
        if (customerAttachmentsIdentifier == null || customerAttachmentsCollectionIdentifiers.includes(customerAttachmentsIdentifier)) {
          return false;
        }
        customerAttachmentsCollectionIdentifiers.push(customerAttachmentsIdentifier);
        return true;
      });
      return [...customerAttachmentsToAdd, ...customerAttachmentsCollection];
    }
    return customerAttachmentsCollection;
  }

  protected convertDateFromClient(customerAttachments: ICustomerAttachments): ICustomerAttachments {
    return Object.assign({}, customerAttachments, {
      attachedDate: customerAttachments.attachedDate?.isValid() ? customerAttachments.attachedDate.toJSON() : undefined,
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
      res.body.forEach((customerAttachments: ICustomerAttachments) => {
        customerAttachments.attachedDate = customerAttachments.attachedDate ? dayjs(customerAttachments.attachedDate) : undefined;
      });
    }
    return res;
  }
}
