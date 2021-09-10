import { IInsurances } from 'app/entities/insurances/insurances.model';
import { IPositions } from 'app/entities/positions/positions.model';
import { ICostCenter } from 'app/entities/cost-center/cost-center.model';
import { IAdministrativeFeesRanges } from 'app/entities/administrative-fees-ranges/administrative-fees-ranges.model';
import { ICustomersGroups } from 'app/entities/customers-groups/customers-groups.model';
import { IFees } from 'app/entities/fees/fees.model';
import { ICustomers } from 'app/entities/customers/customers.model';
import { IStatusAttachments } from 'app/entities/status-attachments/status-attachments.model';
import { IStatus } from 'app/entities/status/status.model';
import { IParking } from 'app/entities/parking/parking.model';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';
import { ICities } from 'app/entities/cities/cities.model';
import { ICompanies } from 'app/entities/companies/companies.model';

export interface IAffiliates {
  id?: number;
  branchName?: string;
  branchNumber?: string;
  useSameCompanyAddress?: boolean | null;
  branchPostalCode?: string | null;
  branchAddress?: string | null;
  branchAddressComplement?: string | null;
  branchAddressNumber?: number | null;
  branchAddressNeighborhood?: string | null;
  branchTelephone?: string | null;
  branchEmail?: string | null;
  responsibleContact?: string | null;
  insurances?: IInsurances[] | null;
  positions?: IPositions[] | null;
  costCenters?: ICostCenter[] | null;
  administrativeFeesRanges?: IAdministrativeFeesRanges[] | null;
  customersGroups?: ICustomersGroups[] | null;
  fees?: IFees[] | null;
  customers?: ICustomers[] | null;
  statusAttachments?: IStatusAttachments[] | null;
  statuses?: IStatus[] | null;
  parkings?: IParking[] | null;
  suppliers?: ISuppliers[] | null;
  employees?: IEmployees[] | null;
  vehicleControls?: IVehicleControls[] | null;
  housings?: IHousing[] | null;
  stateProvinces?: IStateProvinces;
  cities?: ICities;
  companies?: ICompanies;
}

export class Affiliates implements IAffiliates {
  constructor(
    public id?: number,
    public branchName?: string,
    public branchNumber?: string,
    public useSameCompanyAddress?: boolean | null,
    public branchPostalCode?: string | null,
    public branchAddress?: string | null,
    public branchAddressComplement?: string | null,
    public branchAddressNumber?: number | null,
    public branchAddressNeighborhood?: string | null,
    public branchTelephone?: string | null,
    public branchEmail?: string | null,
    public responsibleContact?: string | null,
    public insurances?: IInsurances[] | null,
    public positions?: IPositions[] | null,
    public costCenters?: ICostCenter[] | null,
    public administrativeFeesRanges?: IAdministrativeFeesRanges[] | null,
    public customersGroups?: ICustomersGroups[] | null,
    public fees?: IFees[] | null,
    public customers?: ICustomers[] | null,
    public statusAttachments?: IStatusAttachments[] | null,
    public statuses?: IStatus[] | null,
    public parkings?: IParking[] | null,
    public suppliers?: ISuppliers[] | null,
    public employees?: IEmployees[] | null,
    public vehicleControls?: IVehicleControls[] | null,
    public housings?: IHousing[] | null,
    public stateProvinces?: IStateProvinces,
    public cities?: ICities,
    public companies?: ICompanies
  ) {
    this.useSameCompanyAddress = this.useSameCompanyAddress ?? false;
  }
}

export function getAffiliatesIdentifier(affiliates: IAffiliates): number | undefined {
  return affiliates.id;
}
