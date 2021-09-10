import { ICustomers } from 'app/entities/customers/customers.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';

export interface ICustomersGroups {
  id?: number;
  groupName?: string;
  customers?: ICustomers[] | null;
  vehicleControls?: IVehicleControls[] | null;
  affiliates?: IAffiliates;
}

export class CustomersGroups implements ICustomersGroups {
  constructor(
    public id?: number,
    public groupName?: string,
    public customers?: ICustomers[] | null,
    public vehicleControls?: IVehicleControls[] | null,
    public affiliates?: IAffiliates
  ) {}
}

export function getCustomersGroupsIdentifier(customersGroups: ICustomersGroups): number | undefined {
  return customersGroups.id;
}
