import * as dayjs from 'dayjs';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { ICities } from 'app/entities/cities/cities.model';

export interface IVehicleLocationStatus {
  id?: number;
  vehicleLocationStatusDate?: dayjs.Dayjs;
  vehicleLocationStatusDescription?: string;
  vehicleControls?: IVehicleControls;
  cities?: ICities;
}

export class VehicleLocationStatus implements IVehicleLocationStatus {
  constructor(
    public id?: number,
    public vehicleLocationStatusDate?: dayjs.Dayjs,
    public vehicleLocationStatusDescription?: string,
    public vehicleControls?: IVehicleControls,
    public cities?: ICities
  ) {}
}

export function getVehicleLocationStatusIdentifier(vehicleLocationStatus: IVehicleLocationStatus): number | undefined {
  return vehicleLocationStatus.id;
}
