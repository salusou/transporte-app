import { IVehicleControls } from 'app/entities/vehicle-controls/vehicle-controls.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ScreenType } from 'app/entities/enumerations/screen-type.model';

export interface IStatus {
  id?: number;
  statusName?: string;
  screenType?: ScreenType;
  statusDescriptions?: string | null;
  vehicleControls?: IVehicleControls[] | null;
  housings?: IHousing[] | null;
  affiliates?: IAffiliates;
}

export class Status implements IStatus {
  constructor(
    public id?: number,
    public statusName?: string,
    public screenType?: ScreenType,
    public statusDescriptions?: string | null,
    public vehicleControls?: IVehicleControls[] | null,
    public housings?: IHousing[] | null,
    public affiliates?: IAffiliates
  ) {}
}

export function getStatusIdentifier(status: IStatus): number | undefined {
  return status.id;
}
