import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatusAttachments, getStatusAttachmentsIdentifier } from '../status-attachments.model';

export type EntityResponseType = HttpResponse<IStatusAttachments>;
export type EntityArrayResponseType = HttpResponse<IStatusAttachments[]>;

@Injectable({ providedIn: 'root' })
export class StatusAttachmentsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/status-attachments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(statusAttachments: IStatusAttachments): Observable<EntityResponseType> {
    return this.http.post<IStatusAttachments>(this.resourceUrl, statusAttachments, { observe: 'response' });
  }

  update(statusAttachments: IStatusAttachments): Observable<EntityResponseType> {
    return this.http.put<IStatusAttachments>(
      `${this.resourceUrl}/${getStatusAttachmentsIdentifier(statusAttachments) as number}`,
      statusAttachments,
      { observe: 'response' }
    );
  }

  partialUpdate(statusAttachments: IStatusAttachments): Observable<EntityResponseType> {
    return this.http.patch<IStatusAttachments>(
      `${this.resourceUrl}/${getStatusAttachmentsIdentifier(statusAttachments) as number}`,
      statusAttachments,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusAttachments>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusAttachments[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStatusAttachmentsToCollectionIfMissing(
    statusAttachmentsCollection: IStatusAttachments[],
    ...statusAttachmentsToCheck: (IStatusAttachments | null | undefined)[]
  ): IStatusAttachments[] {
    const statusAttachments: IStatusAttachments[] = statusAttachmentsToCheck.filter(isPresent);
    if (statusAttachments.length > 0) {
      const statusAttachmentsCollectionIdentifiers = statusAttachmentsCollection.map(
        statusAttachmentsItem => getStatusAttachmentsIdentifier(statusAttachmentsItem)!
      );
      const statusAttachmentsToAdd = statusAttachments.filter(statusAttachmentsItem => {
        const statusAttachmentsIdentifier = getStatusAttachmentsIdentifier(statusAttachmentsItem);
        if (statusAttachmentsIdentifier == null || statusAttachmentsCollectionIdentifiers.includes(statusAttachmentsIdentifier)) {
          return false;
        }
        statusAttachmentsCollectionIdentifiers.push(statusAttachmentsIdentifier);
        return true;
      });
      return [...statusAttachmentsToAdd, ...statusAttachmentsCollection];
    }
    return statusAttachmentsCollection;
  }
}
