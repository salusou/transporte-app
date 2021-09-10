import { ICustomersContacts } from 'app/entities/customers-contacts/customers-contacts.model';
import { ICustomerAttachments } from 'app/entities/customer-attachments/customer-attachments.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ICities } from 'app/entities/cities/cities.model';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';

export interface ICustomers {
  id?: number;
  customerName?: string;
  active?: boolean;
  customerNumber?: string;
  customerPostalCode?: string | null;
  customerAddress?: string | null;
  customerAddressComplement?: string | null;
  customerAddressNumber?: number | null;
  customerAddressNeighborhood?: string | null;
  customerTelephone?: string | null;
  paymentTerm?: number;
  customersContacts?: ICustomersContacts[] | null;
  customerAttachments?: ICustomerAttachments[] | null;
  vehicleControls?: IVehicleControls[] | null;
  housings?: IHousing[] | null;
  affiliates?: IAffiliates;
  cities?: ICities;
  customersGroups?: ICustomersGroups | null;
}

export class Customers implements ICustomers {
  constructor(
    public id?: number,
    public customerName?: string,
    public active?: boolean,
    public customerNumber?: string,
    public customerPostalCode?: string | null,
    public customerAddress?: string | null,
    public customerAddressComplement?: string | null,
    public customerAddressNumber?: number | null,
    public customerAddressNeighborhood?: string | null,
    public customerTelephone?: string | null,
    public paymentTerm?: number,
    public customersContacts?: ICustomersContacts[] | null,
    public customerAttachments?: ICustomerAttachments[] | null,
    public vehicleControls?: IVehicleControls[] | null,
    public housings?: IHousing[] | null,
    public affiliates?: IAffiliates,
    public cities?: ICities,
    public customersGroups?: ICustomersGroups | null
  ) {
    this.active = this.active ?? false;
  }
}

export function getCustomersIdentifier(customers: ICustomers): number | undefined {
  return customers.id;
}
