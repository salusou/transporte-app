import * as dayjs from 'dayjs';
import { IHousingVehicleItem } from 'app/entities/housing-vehicle-item/housing-vehicle-item.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { IStatus } from 'app/entities/status/status.model';
import { ICustomers } from 'app/entities/customers/customers.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { IParking } from 'app/entities/parking/parking.model';
import { ICostCenter } from 'app/entities/cost-center/cost-center.model';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { ICities } from 'app/entities/cities/cities.model';

export interface IHousing {
  id?: number;
  housingDate?: dayjs.Dayjs;
  housingEntranceDate?: dayjs.Dayjs;
  housingExit?: dayjs.Dayjs | null;
  housingReceiptNumber?: number | null;
  housingDailyPrice?: number;
  housingDescription?: string | null;
  housingVehicleItems?: IHousingVehicleItem[] | null;
  affiliates?: IAffiliates;
  status?: IStatus;
  customers?: ICustomers;
  employees?: IEmployees;
  parking?: IParking | null;
  costCenter?: ICostCenter | null;
  suppliers?: ISuppliers;
  cities?: ICities;
}

export class Housing implements IHousing {
  constructor(
    public id?: number,
    public housingDate?: dayjs.Dayjs,
    public housingEntranceDate?: dayjs.Dayjs,
    public housingExit?: dayjs.Dayjs | null,
    public housingReceiptNumber?: number | null,
    public housingDailyPrice?: number,
    public housingDescription?: string | null,
    public housingVehicleItems?: IHousingVehicleItem[] | null,
    public affiliates?: IAffiliates,
    public status?: IStatus,
    public customers?: ICustomers,
    public employees?: IEmployees,
    public parking?: IParking | null,
    public costCenter?: ICostCenter | null,
    public suppliers?: ISuppliers,
    public cities?: ICities
  ) {}
}

export function getHousingIdentifier(housing: IHousing): number | undefined {
  return housing.id;
}
