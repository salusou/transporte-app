import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { IEmployees } from 'app/entities/employees/employees.model';
import { ICities } from 'app/entities/cities/cities.model';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';

export interface ICompanies {
  id?: number;
  companyName?: string;
  tradeName?: string | null;
  companyNumber?: string;
  postalCode?: string | null;
  companyAddress?: string | null;
  companyAddressComplement?: string | null;
  companyAddressNumber?: number | null;
  companyAddressNeighborhood?: string | null;
  companyTelephone?: string | null;
  companyEmail?: string | null;
  responsibleContact?: string | null;
  affiliates?: IAffiliates[] | null;
  employees?: IEmployees[] | null;
  cities?: ICities;
  stateProvinces?: IStateProvinces;
}

export class Companies implements ICompanies {
  constructor(
    public id?: number,
    public companyName?: string,
    public tradeName?: string | null,
    public companyNumber?: string,
    public postalCode?: string | null,
    public companyAddress?: string | null,
    public companyAddressComplement?: string | null,
    public companyAddressNumber?: number | null,
    public companyAddressNeighborhood?: string | null,
    public companyTelephone?: string | null,
    public companyEmail?: string | null,
    public responsibleContact?: string | null,
    public affiliates?: IAffiliates[] | null,
    public employees?: IEmployees[] | null,
    public cities?: ICities,
    public stateProvinces?: IStateProvinces
  ) {}
}

export function getCompaniesIdentifier(companies: ICompanies): number | undefined {
  return companies.id;
}
