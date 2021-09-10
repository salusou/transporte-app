import { IHousing } from 'app/entities/housing/housing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';

export interface ICostCenter {
  id?: number;
  costCenterName?: string;
  housings?: IHousing[] | null;
  affiliates?: IAffiliates;
}

export class CostCenter implements ICostCenter {
  constructor(public id?: number, public costCenterName?: string, public housings?: IHousing[] | null, public affiliates?: IAffiliates) {}
}

export function getCostCenterIdentifier(costCenter: ICostCenter): number | undefined {
  return costCenter.id;
}
