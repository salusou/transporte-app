import * as dayjs from 'dayjs';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IEmployees } from 'app/entities/employees/employees.model';

export interface IVehicleControlHistory {
  id?: number;
  vehicleControlHistoryDate?: dayjs.Dayjs;
  vehicleControlHistoryDescription?: string;
  vehicleControls?: IVehicleControls;
  employees?: IEmployees;
}

export class VehicleControlHistory implements IVehicleControlHistory {
  constructor(
    public id?: number,
    public vehicleControlHistoryDate?: dayjs.Dayjs,
    public vehicleControlHistoryDescription?: string,
    public vehicleControls?: IVehicleControls,
    public employees?: IEmployees
  ) {}
}

export function getVehicleControlHistoryIdentifier(vehicleControlHistory: IVehicleControlHistory): number | undefined {
  return vehicleControlHistory.id;
}
