import * as dayjs from 'dayjs';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { ISuppliers } from 'app/entities/suppliers/suppliers.model';
import { ICities } from 'app/entities/cities/cities.model';
import { IVehicleControlItem } from 'app/entities/vehicle-control-item/vehicle-control-item.model';
import { DriverType } from 'app/entities/enumerations/driver-type.model';

export interface IVehicleControlExpenses {
  id?: number;
  vehicleControlExpensesDescription?: string;
  vehicleControlExpensesDriverType?: DriverType | null;
  vehicleControlExpensesPurchaseOrder?: string | null;
  vehicleControlExpensesDueDate?: dayjs.Dayjs | null;
  vehicleControlExpensesPaymentDate?: dayjs.Dayjs | null;
  vehicleControlExpensesBillingTotalValue?: number | null;
  vehicleControlExpensesDriverCommission?: boolean | null;
  vehicleControls?: IVehicleControls;
  suppliers?: ISuppliers;
  origin?: ICities;
  destination?: ICities;
  vehicleControlItem?: IVehicleControlItem;
}

export class VehicleControlExpenses implements IVehicleControlExpenses {
  constructor(
    public id?: number,
    public vehicleControlExpensesDescription?: string,
    public vehicleControlExpensesDriverType?: DriverType | null,
    public vehicleControlExpensesPurchaseOrder?: string | null,
    public vehicleControlExpensesDueDate?: dayjs.Dayjs | null,
    public vehicleControlExpensesPaymentDate?: dayjs.Dayjs | null,
    public vehicleControlExpensesBillingTotalValue?: number | null,
    public vehicleControlExpensesDriverCommission?: boolean | null,
    public vehicleControls?: IVehicleControls,
    public suppliers?: ISuppliers,
    public origin?: ICities,
    public destination?: ICities,
    public vehicleControlItem?: IVehicleControlItem
  ) {
    this.vehicleControlExpensesDriverCommission = this.vehicleControlExpensesDriverCommission ?? false;
  }
}

export function getVehicleControlExpensesIdentifier(vehicleControlExpenses: IVehicleControlExpenses): number | undefined {
  return vehicleControlExpenses.id;
}
