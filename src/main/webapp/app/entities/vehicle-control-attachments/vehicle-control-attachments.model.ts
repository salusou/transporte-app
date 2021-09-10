import * as dayjs from 'dayjs';
import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';

export interface IVehicleControlAttachments {
  id?: number;
  attachImageContentType?: string | null;
  attachImage?: string | null;
  attachUrl?: string;
  attachDescription?: string;
  attachedDate?: dayjs.Dayjs;
  vehicleControls?: IVehicleControls;
}

export class VehicleControlAttachments implements IVehicleControlAttachments {
  constructor(
    public id?: number,
    public attachImageContentType?: string | null,
    public attachImage?: string | null,
    public attachUrl?: string,
    public attachDescription?: string,
    public attachedDate?: dayjs.Dayjs,
    public vehicleControls?: IVehicleControls
  ) {}
}

export function getVehicleControlAttachmentsIdentifier(vehicleControlAttachments: IVehicleControlAttachments): number | undefined {
  return vehicleControlAttachments.id;
}
