import { ICustomerAttachments } from 'app/entities/customer-attachments/customer-attachments.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { AttachmentType } from 'app/entities/enumerations/attachment-type.model';

export interface IStatusAttachments {
  id?: number;
  statusName?: string;
  statusDescriptions?: string | null;
  statusObs?: string | null;
  attachmentType?: AttachmentType | null;
  customerAttachments?: ICustomerAttachments[] | null;
  affiliates?: IAffiliates;
}

export class StatusAttachments implements IStatusAttachments {
  constructor(
    public id?: number,
    public statusName?: string,
    public statusDescriptions?: string | null,
    public statusObs?: string | null,
    public attachmentType?: AttachmentType | null,
    public customerAttachments?: ICustomerAttachments[] | null,
    public affiliates?: IAffiliates
  ) {}
}

export function getStatusAttachmentsIdentifier(statusAttachments: IStatusAttachments): number | undefined {
  return statusAttachments.id;
}
