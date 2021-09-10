import * as dayjs from 'dayjs';
import { IVehicleInspections } from 'app/entities/vehicle-inspections/vehicle-inspections.model';
import { IVehicleControlExpenses } from 'app/entities/vehicle-control-expenses/vehicle-control-expenses.model';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { StatusType } from 'app/entities/enumerations/status-type.model';
import { VehicleType } from 'app/entities/enumerations/vehicle-type.model';

export interface IVehicleControlItem {
  id?: number;
  vehicleControlStatus?: StatusType;
  vehicleControlItemPlate?: string;
  vehicleControlItemType?: VehicleType;
  vehicleControlItemFipeCode?: string | null;
  vehicleControlItemYear?: string | null;
  vehicleControlItemFuel?: string | null;
  vehicleControlItemBranch?: string | null;
  vehicleControlItemModel?: string | null;
  vehicleControlItemFuelAbbreviation?: string | null;
  vehicleControlItemReferenceMonth?: string | null;
  vehicleControlItemValue?: number;
  vehicleControlItemShippingValue?: number;
  vehicleControlItemCTE?: string | null;
  vehicleControlItemCTEDate?: dayjs.Dayjs | null;
  vehicleInspections?: IVehicleInspections[] | null;
  vehicleControlExpenses?: IVehicleControlExpenses[] | null;
  vehicleControls?: IVehicleControls;
}

export class VehicleControlItem implements IVehicleControlItem {
  constructor(
    public id?: number,
    public vehicleControlStatus?: StatusType,
    public vehicleControlItemPlate?: string,
    public vehicleControlItemType?: VehicleType,
    public vehicleControlItemFipeCode?: string | null,
    public vehicleControlItemYear?: string | null,
    public vehicleControlItemFuel?: string | null,
    public vehicleControlItemBranch?: string | null,
    public vehicleControlItemModel?: string | null,
    public vehicleControlItemFuelAbbreviation?: string | null,
    public vehicleControlItemReferenceMonth?: string | null,
    public vehicleControlItemValue?: number,
    public vehicleControlItemShippingValue?: number,
    public vehicleControlItemCTE?: string | null,
    public vehicleControlItemCTEDate?: dayjs.Dayjs | null,
    public vehicleInspections?: IVehicleInspections[] | null,
    public vehicleControlExpenses?: IVehicleControlExpenses[] | null,
    public vehicleControls?: IVehicleControls
  ) {}
}

export function getVehicleControlItemIdentifier(vehicleControlItem: IVehicleControlItem): number | undefined {
  return vehicleControlItem.id;
}
