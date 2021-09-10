import { IParkingSectorSpace } from 'app/entities/parking-sector-space/parking-sector-space.model';
import { IHousingVehicleItem } from 'app/entities/housing-vehicle-item/housing-vehicle-item.model';
import { IParking } from 'app/entities/parking/parking.model';

export interface IParkingSector {
  id?: number;
  active?: boolean;
  sectorName?: string;
  parkingSpace?: number;
  parkingNumbersBegin?: number | null;
  parkingNumbersFinal?: number | null;
  parkingSectorSpaces?: IParkingSectorSpace[] | null;
  housingVehicleItems?: IHousingVehicleItem[] | null;
  parking?: IParking;
}

export class ParkingSector implements IParkingSector {
  constructor(
    public id?: number,
    public active?: boolean,
    public sectorName?: string,
    public parkingSpace?: number,
    public parkingNumbersBegin?: number | null,
    public parkingNumbersFinal?: number | null,
    public parkingSectorSpaces?: IParkingSectorSpace[] | null,
    public housingVehicleItems?: IHousingVehicleItem[] | null,
    public parking?: IParking
  ) {
    this.active = this.active ?? false;
  }
}

export function getParkingSectorIdentifier(parkingSector: IParkingSector): number | undefined {
  return parkingSector.id;
}
