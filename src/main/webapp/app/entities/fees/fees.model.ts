import * as dayjs from 'dayjs';
import { IVehicleControlBilling } from 'app/entities/vehicle-control-billing/vehicle-control-billing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';

export interface IFees {
  id?: number;
  feeDate?: dayjs.Dayjs;
  feeDriverCommission?: number;
  feeFinancialCost?: number;
  feeTaxes?: number;
  feeDescriptions?: string | null;
  vehicleControlBillings?: IVehicleControlBilling[] | null;
  affiliates?: IAffiliates;
}

export class Fees implements IFees {
  constructor(
    public id?: number,
    public feeDate?: dayjs.Dayjs,
    public feeDriverCommission?: number,
    public feeFinancialCost?: number,
    public feeTaxes?: number,
    public feeDescriptions?: string | null,
    public vehicleControlBillings?: IVehicleControlBilling[] | null,
    public affiliates?: IAffiliates
  ) {}
}

export function getFeesIdentifier(fees: IFees): number | undefined {
  return fees.id;
}
