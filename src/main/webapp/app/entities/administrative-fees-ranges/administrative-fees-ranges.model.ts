import { IAffiliates } from 'app/entities/affiliates/affiliates.model';

export interface IAdministrativeFeesRanges {
  id?: number;
  minRange?: number;
  maxRange?: number;
  aliquot?: number;
  affiliates?: IAffiliates;
}

export class AdministrativeFeesRanges implements IAdministrativeFeesRanges {
  constructor(
    public id?: number,
    public minRange?: number,
    public maxRange?: number,
    public aliquot?: number,
    public affiliates?: IAffiliates
  ) {}
}

export function getAdministrativeFeesRangesIdentifier(administrativeFeesRanges: IAdministrativeFeesRanges): number | undefined {
  return administrativeFeesRanges.id;
}
