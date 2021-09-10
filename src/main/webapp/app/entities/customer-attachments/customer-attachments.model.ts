import * as dayjs from 'dayjs';
import { ICustomers } from 'app/entities/customers/customers.model';
import { IStatusAttachments } from 'app/entities/status-attachments/status-attachments.model';

export interface ICustomerAttachments {
  id?: number;
  attachImageContentType?: string | null;
  attachImage?: string | null;
  attachUrl?: string;
  attachDescription?: string;
  attachedDate?: dayjs.Dayjs;
  customers?: ICustomers;
  statusAttachments?: IStatusAttachments;
}

export class CustomerAttachments implements ICustomerAttachments {
  constructor(
    public id?: number,
    public attachImageContentType?: string | null,
    public attachImage?: string | null,
    public attachUrl?: string,
    public attachDescription?: string,
    public attachedDate?: dayjs.Dayjs,
    public customers?: ICustomers,
    public statusAttachments?: IStatusAttachments
  ) {}
}

export function getCustomerAttachmentsIdentifier(customerAttachments: ICustomerAttachments): number | undefined {
  return customerAttachments.id;
}
