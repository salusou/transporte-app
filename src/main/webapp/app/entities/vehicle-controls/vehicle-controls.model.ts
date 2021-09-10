import * as dayjs from 'dayjs';
import { IVehicleLocationStatus } from 'app/entities/vehicle-location-status/vehicle-location-status.model';
import { IVehicleControlHistory } from 'app/entities/vehicle-control-history/vehicle-control-history.model';
import { IVehicleControlBilling } from 'app/entities/vehicle-control-billing/vehicle-control-billing.model';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { IVehicleControlAttachments } from 'app/entities/vehicle-control-attachments/vehicle-control-attachments.model';
import { IVehicleControlExpenses } from 'app/entities/vehicle-control-expenses/vehicle-control-expenses.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ICustomers } from 'app/entities/customers/customers.model';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { ICities } from 'app/entities/cities/cities.model';
import { IStatus } from 'app/entities/status/status.model';

export interface IVehicleControls {
  id?: number;
  vehicleControlAuthorizedOrder?: string | null;
  vehicleControlRequest?: string | null;
  vehicleControlSinister?: string | null;
  vehicleControlDate?: dayjs.Dayjs;
  vehicleControlKm?: number | null;
  vehicleControlPlate?: string | null;
  vehicleControlAmount?: number | null;
  vehicleControlPrice?: number | null;
  vehicleControlMaximumDeliveryDate?: dayjs.Dayjs | null;
  vehicleControlCollectionForecast?: dayjs.Dayjs | null;
  vehicleControlCollectionDeliveryForecast?: dayjs.Dayjs | null;
  vehicleControlDateCollected?: dayjs.Dayjs | null;
  vehicleControlDeliveryDate?: dayjs.Dayjs | null;
  vehicleLocationStatuses?: IVehicleLocationStatus[] | null;
  vehicleControlHistories?: IVehicleControlHistory[] | null;
  vehicleControlBillings?: IVehicleControlBilling[] | null;
  vehicleControlItems?: IVehicleControlItem[] | null;
  vehicleControlAttachments?: IVehicleControlAttachments[] | null;
  vehicleControlExpenses?: IVehicleControlExpenses[] | null;
  affiliates?: IAffiliates;
  customers?: ICustomers;
  customersGroups?: ICustomersGroups;
  employees?: IEmployees;
  origin?: ICities;
  destination?: ICities;
  status?: IStatus;
}

export class VehicleControls implements IVehicleControls {
  constructor(
    public id?: number,
    public vehicleControlAuthorizedOrder?: string | null,
    public vehicleControlRequest?: string | null,
    public vehicleControlSinister?: string | null,
    public vehicleControlDate?: dayjs.Dayjs,
    public vehicleControlKm?: number | null,
    public vehicleControlPlate?: string | null,
    public vehicleControlAmount?: number | null,
    public vehicleControlPrice?: number | null,
    public vehicleControlMaximumDeliveryDate?: dayjs.Dayjs | null,
    public vehicleControlCollectionForecast?: dayjs.Dayjs | null,
    public vehicleControlCollectionDeliveryForecast?: dayjs.Dayjs | null,
    public vehicleControlDateCollected?: dayjs.Dayjs | null,
    public vehicleControlDeliveryDate?: dayjs.Dayjs | null,
    public vehicleLocationStatuses?: IVehicleLocationStatus[] | null,
    public vehicleControlHistories?: IVehicleControlHistory[] | null,
    public vehicleControlBillings?: IVehicleControlBilling[] | null,
    public vehicleControlItems?: IVehicleControlItem[] | null,
    public vehicleControlAttachments?: IVehicleControlAttachments[] | null,
    public vehicleControlExpenses?: IVehicleControlExpenses[] | null,
    public affiliates?: IAffiliates,
    public customers?: ICustomers,
    public customersGroups?: ICustomersGroups,
    public employees?: IEmployees,
    public origin?: ICities,
    public destination?: ICities,
    public status?: IStatus
  ) {}
}

export function getVehicleControlsIdentifier(vehicleControls: IVehicleControls): number | undefined {
  return vehicleControls.id;
}
