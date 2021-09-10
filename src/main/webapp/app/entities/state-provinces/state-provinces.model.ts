import { ICities } from 'app/entities/cities/cities.model';
import { ICompanies } from 'app/entities/companies/companies.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { IInsurances } from 'app/entities/insurances/insurances.model';
import { ICountries } from 'app/entities/countries/countries.model';

export interface IStateProvinces {
  id?: number;
  stateName?: string;
  abbreviation?: string;
  cities?: ICities[] | null;
  companies?: ICompanies[] | null;
  affiliates?: IAffiliates[] | null;
  toInsurances?: IInsurances[] | null;
  forInsurances?: IInsurances[] | null;
  countries?: ICountries;
}

export class StateProvinces implements IStateProvinces {
  constructor(
    public id?: number,
    public stateName?: string,
    public abbreviation?: string,
    public cities?: ICities[] | null,
    public companies?: ICompanies[] | null,
    public affiliates?: IAffiliates[] | null,
    public toInsurances?: IInsurances[] | null,
    public forInsurances?: IInsurances[] | null,
    public countries?: ICountries
  ) {}
}

export function getStateProvincesIdentifier(stateProvinces: IStateProvinces): number | undefined {
  return stateProvinces.id;
}
