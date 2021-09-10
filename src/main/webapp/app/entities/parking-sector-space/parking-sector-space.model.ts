import * as dayjs from 'dayjs';
import { IHousingVehicleItem } from 'app/entities/housing-vehicle-item/housing-vehicle-item.model';
import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { ParkingSpaceStatus } from 'app/entities/enumerations/parking-space-status.model';

export interface IParkingSectorSpace {
  id?: number;
  parkingNumber?: number;
  parkingStatus?: ParkingSpaceStatus;
  parkingEntryDate?: dayjs.Dayjs | null;
  parkingDepartureDate?: dayjs.Dayjs | null;
  parkingHousingItemId?: number | null;
  housingVehicleItems?: IHousingVehicleItem[] | null;
  parkingSector?: IParkingSector;
}

export class ParkingSectorSpace implements IParkingSectorSpace {
  constructor(
    public id?: number,
    public parkingNumber?: number,
    public parkingStatus?: ParkingSpaceStatus,
    public parkingEntryDate?: dayjs.Dayjs | null,
    public parkingDepartureDate?: dayjs.Dayjs | null,
    public parkingHousingItemId?: number | null,
    public housingVehicleItems?: IHousingVehicleItem[] | null,
    public parkingSector?: IParkingSector
  ) {}
}

export function getParkingSectorSpaceIdentifier(parkingSectorSpace: IParkingSectorSpace): number | undefined {
  return parkingSectorSpace.id;
}
