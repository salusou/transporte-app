import { IParkingSector } from 'app/entities/parking-sector/parking-sector.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ICities } from 'app/entities/cities/cities.model';

export interface IParking {
  id?: number;
  active?: boolean;
  parkingName?: string;
  parkingTradeName?: string | null;
  parkingNumber?: string | null;
  parkingPostalCode?: string | null;
  parkingAddress?: string | null;
  parkingAddressComplement?: string | null;
  parkingAddressNumber?: number | null;
  parkingAddressNeighborhood?: string | null;
  parkingTelephone?: string | null;
  parkingEmail?: string | null;
  parkingContactName?: string | null;
  parkingSectors?: IParkingSector[] | null;
  housings?: IHousing[] | null;
  affiliates?: IAffiliates;
  cities?: ICities;
}

export class Parking implements IParking {
  constructor(
    public id?: number,
    public active?: boolean,
    public parkingName?: string,
    public parkingTradeName?: string | null,
    public parkingNumber?: string | null,
    public parkingPostalCode?: string | null,
    public parkingAddress?: string | null,
    public parkingAddressComplement?: string | null,
    public parkingAddressNumber?: number | null,
    public parkingAddressNeighborhood?: string | null,
    public parkingTelephone?: string | null,
    public parkingEmail?: string | null,
    public parkingContactName?: string | null,
    public parkingSectors?: IParkingSector[] | null,
    public housings?: IHousing[] | null,
    public affiliates?: IAffiliates,
    public cities?: ICities
  ) {
    this.active = this.active ?? false;
  }
}

export function getParkingIdentifier(parking: IParking): number | undefined {
  return parking.id;
}
