import * as dayjs from 'dayjs';
import { IEmployeeAttachments } from 'app/entities/employee-attachments/employee-attachments.model';
import { IEmployeeComponentsData } from 'app/entities/employee-components-data/employee-components-data.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IVehicleControlHistory } from 'app/entities/vehicle-control-history/vehicle-control-history.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { ICompanies } from 'app/entities/companies/companies.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ICities } from 'app/entities/cities/cities.model';
import { IPositions } from 'app/entities/positions/positions.model';

export interface IEmployees {
  id?: number;
  active?: boolean;
  employeeFullName?: string;
  employeeEmail?: string;
  employeeIdentificationNumber?: string | null;
  employeeNumber?: string | null;
  employeePostalCode?: string | null;
  employeeAddress?: string | null;
  employeeAddressComplement?: string | null;
  employeeAddressNumber?: number | null;
  employeeAddressNeighborhood?: string | null;
  employeeBirthday?: dayjs.Dayjs | null;
  employeeAttachments?: IEmployeeAttachments[] | null;
  employeeComponentsData?: IEmployeeComponentsData[] | null;
  vehicleControls?: IVehicleControls[] | null;
  vehicleControlHistories?: IVehicleControlHistory[] | null;
  housings?: IHousing[] | null;
  companies?: ICompanies;
  affiliates?: IAffiliates | null;
  cities?: ICities;
  positions?: IPositions;
}

export class Employees implements IEmployees {
  constructor(
    public id?: number,
    public active?: boolean,
    public employeeFullName?: string,
    public employeeEmail?: string,
    public employeeIdentificationNumber?: string | null,
    public employeeNumber?: string | null,
    public employeePostalCode?: string | null,
    public employeeAddress?: string | null,
    public employeeAddressComplement?: string | null,
    public employeeAddressNumber?: number | null,
    public employeeAddressNeighborhood?: string | null,
    public employeeBirthday?: dayjs.Dayjs | null,
    public employeeAttachments?: IEmployeeAttachments[] | null,
    public employeeComponentsData?: IEmployeeComponentsData[] | null,
    public vehicleControls?: IVehicleControls[] | null,
    public vehicleControlHistories?: IVehicleControlHistory[] | null,
    public housings?: IHousing[] | null,
    public companies?: ICompanies,
    public affiliates?: IAffiliates | null,
    public cities?: ICities,
    public positions?: IPositions
  ) {
    this.active = this.active ?? false;
  }
}

export function getEmployeesIdentifier(employees: IEmployees): number | undefined {
  return employees.id;
}
