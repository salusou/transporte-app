import { ICustomers } from 'app/entities/customers/customers.model';

export interface ICustomersContacts {
  id?: number;
  contactName?: string;
  contactTelephone?: string;
  contactEmail?: string | null;
  customers?: ICustomers;
}

export class CustomersContacts implements ICustomersContacts {
  constructor(
    public id?: number,
    public contactName?: string,
    public contactTelephone?: string,
    public contactEmail?: string | null,
    public customers?: ICustomers
  ) {}
}

export function getCustomersContactsIdentifier(customersContacts: ICustomersContacts): number | undefined {
  return customersContacts.id;
}
