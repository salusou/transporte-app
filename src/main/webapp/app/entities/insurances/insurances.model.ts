import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { IStateProvinces } from 'app/entities/state-provinces/state-provinces.model';

export interface IInsurances {
  id?: number;
  insurancesPercent?: number;
  affiliates?: IAffiliates;
  toStateProvince?: IStateProvinces;
  forStateProvince?: IStateProvinces;
}

export class Insurances implements IInsurances {
  constructor(
    public id?: number,
    public insurancesPercent?: number,
    public affiliates?: IAffiliates,
    public toStateProvince?: IStateProvinces,
    public forStateProvince?: IStateProvinces
  ) {}
}

export function getInsurancesIdentifier(insurances: IInsurances): number | undefined {
  return insurances.id;
}
