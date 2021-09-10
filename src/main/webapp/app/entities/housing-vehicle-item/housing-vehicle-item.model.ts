import { IHousing } from 'app/entities/housing/housing.model';
import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { IParkingSectorSpace } from 'app/entities/parking-sector-space/parking-sector-space.model';
import { StatusType } from 'app/entities/enumerations/status-type.model';
import { VehicleType } from 'app/entities/enumerations/vehicle-type.model';

export interface IHousingVehicleItem {
  id?: number;
  housingVehicleItemStatus?: StatusType;
  housingVehicleItemPlate?: string;
  housingVehicleItemType?: VehicleType;
  housingVehicleItemFipeCode?: string | null;
  housingVehicleItemYear?: string | null;
  housingVehicleItemFuel?: string | null;
  housingVehicleItemBranch?: string | null;
  housingVehicleItemModel?: string | null;
  housingVehicleItemFuelAbbreviation?: string | null;
  housingVehicleItemReferenceMonth?: string | null;
  housingVehicleItemValue?: number;
  housingVehicleItemShippingValue?: number;
  housing?: IHousing;
  parkingSector?: IParkingSector | null;
  parkingSectorSpace?: IParkingSectorSpace | null;
}

export class HousingVehicleItem implements IHousingVehicleItem {
  constructor(
    public id?: number,
    public housingVehicleItemStatus?: StatusType,
    public housingVehicleItemPlate?: string,
    public housingVehicleItemType?: VehicleType,
    public housingVehicleItemFipeCode?: string | null,
    public housingVehicleItemYear?: string | null,
    public housingVehicleItemFuel?: string | null,
    public housingVehicleItemBranch?: string | null,
    public housingVehicleItemModel?: string | null,
    public housingVehicleItemFuelAbbreviation?: string | null,
    public housingVehicleItemReferenceMonth?: string | null,
    public housingVehicleItemValue?: number,
    public housingVehicleItemShippingValue?: number,
    public housing?: IHousing,
    public parkingSector?: IParkingSector | null,
    public parkingSectorSpace?: IParkingSectorSpace | null
  ) {}
}

export function getHousingVehicleItemIdentifier(housingVehicleItem: IHousingVehicleItem): number | undefined {
  return housingVehicleItem.id;
}
