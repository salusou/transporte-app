import { ICompanies } from 'app/entities/companies/companies.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ICustomers } from 'app/entities/customers/customers.model';
import { IParking } from 'app/entities/parking/parking.model';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IVehicleLocationStatus } from 'app/entities/vehicle-location-status/vehicle-location-status.model';
import { IVehicleControlExpenses } from 'app/entities/vehicle-control-expenses/vehicle-control-expenses.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';

export interface ICities {
  id?: number;
  cityName?: string;
  latitude?: number | null;
  longitude?: number | null;
  companies?: ICompanies[] | null;
  affiliates?: IAffiliates[] | null;
  customers?: ICustomers[] | null;
  parkings?: IParking[] | null;
  suppliers?: ISuppliers[] | null;
  employees?: IEmployees[] | null;
  originVehicleControls?: IVehicleControls[] | null;
  destinationVehicleControls?: IVehicleControls[] | null;
  vehicleLocationStatuses?: IVehicleLocationStatus[] | null;
  originVehicleControlExpenses?: IVehicleControlExpenses[] | null;
  destinationVehicleControlExpenses?: IVehicleControlExpenses[] | null;
  housings?: IHousing[] | null;
  stateProvinces?: IStateProvinces;
}

export class Cities implements ICities {
  constructor(
    public id?: number,
    public cityName?: string,
    public latitude?: number | null,
    public longitude?: number | null,
    public companies?: ICompanies[] | null,
    public affiliates?: IAffiliates[] | null,
    public customers?: ICustomers[] | null,
    public parkings?: IParking[] | null,
    public suppliers?: ISuppliers[] | null,
    public employees?: IEmployees[] | null,
    public originVehicleControls?: IVehicleControls[] | null,
    public destinationVehicleControls?: IVehicleControls[] | null,
    public vehicleLocationStatuses?: IVehicleLocationStatus[] | null,
    public originVehicleControlExpenses?: IVehicleControlExpenses[] | null,
    public destinationVehicleControlExpenses?: IVehicleControlExpenses[] | null,
    public housings?: IHousing[] | null,
    public stateProvinces?: IStateProvinces
  ) {}
}

export function getCitiesIdentifier(cities: ICities): number | undefined {
  return cities.id;
}
