import * as dayjs from 'dayjs';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IFees } from 'app/entities/fees/fees.model';

export interface IVehicleControlBilling {
  id?: number;
  vehicleControlBillingDate?: dayjs.Dayjs;
  vehicleControlBillingExpirationDate?: dayjs.Dayjs | null;
  vehicleControlBillingPaymentDate?: dayjs.Dayjs | null;
  vehicleControlBillingSellerCommission?: boolean | null;
  vehicleControlBillingDriverCommission?: boolean | null;
  vehicleControlBillingAmount?: number;
  vehicleControlBillingTotalValue?: number;
  vehicleControlBillingInsuranceDiscount?: number | null;
  vehicleControlBillingCustomerBank?: string | null;
  vehicleControlBillingAnticipate?: boolean | null;
  vehicleControlBillingManifest?: string | null;
  vehicleControls?: IVehicleControls;
  fees?: IFees;
}

export class VehicleControlBilling implements IVehicleControlBilling {
  constructor(
    public id?: number,
    public vehicleControlBillingDate?: dayjs.Dayjs,
    public vehicleControlBillingExpirationDate?: dayjs.Dayjs | null,
    public vehicleControlBillingPaymentDate?: dayjs.Dayjs | null,
    public vehicleControlBillingSellerCommission?: boolean | null,
    public vehicleControlBillingDriverCommission?: boolean | null,
    public vehicleControlBillingAmount?: number,
    public vehicleControlBillingTotalValue?: number,
    public vehicleControlBillingInsuranceDiscount?: number | null,
    public vehicleControlBillingCustomerBank?: string | null,
    public vehicleControlBillingAnticipate?: boolean | null,
    public vehicleControlBillingManifest?: string | null,
    public vehicleControls?: IVehicleControls,
    public fees?: IFees
  ) {
    this.vehicleControlBillingSellerCommission = this.vehicleControlBillingSellerCommission ?? false;
    this.vehicleControlBillingDriverCommission = this.vehicleControlBillingDriverCommission ?? false;
    this.vehicleControlBillingAnticipate = this.vehicleControlBillingAnticipate ?? false;
  }
}

export function getVehicleControlBillingIdentifier(vehicleControlBilling: IVehicleControlBilling): number | undefined {
  return vehicleControlBilling.id;
}
