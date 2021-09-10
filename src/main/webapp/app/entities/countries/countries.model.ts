import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';

export interface ICountries {
  id?: number;
  countryName?: string;
  iso2?: string;
  codeArea?: number | null;
  language?: string | null;
  stateProvinces?: IStateProvinces[] | null;
}

export class Countries implements ICountries {
  constructor(
    public id?: number,
    public countryName?: string,
    public iso2?: string,
    public codeArea?: number | null,
    public language?: string | null,
    public stateProvinces?: IStateProvinces[] | null
  ) {}
}

export function getCountriesIdentifier(countries: ICountries): number | undefined {
  return countries.id;
}
