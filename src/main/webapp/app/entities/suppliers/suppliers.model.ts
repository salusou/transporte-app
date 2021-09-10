import { ISupplierBanksInfo } from 'app/entities/supplier-banks-info/supplier-banks-info.model';
import { ISupplierContacts } from 'app/entities/supplier-contacts/supplier-contacts.model';
import { IVehicleControlExpenses } from 'app/entities/vehicle-control-expenses/vehicle-control-expenses.model';
import { IHousing } from 'app/entities/housing/housing.model';
import { IAffiliates } from 'app/entities/affiliates/affiliates.model';
import { ICities } from 'app/entities/cities/cities.model';
import { IServiceProvided } from 'app/entities/service-provided/service-provided.model';

export interface ISuppliers {
  id?: number;
  supplierName?: string;
  supplierNumber?: string | null;
  supplierPostalCode?: string | null;
  supplierAddress?: string | null;
  supplierAddressComplement?: string | null;
  supplierAddressNumber?: number | null;
  supplierAddressNeighborhood?: string | null;
  supplierTelephone?: string | null;
  supplierEmail?: string | null;
  supplierContactName?: string | null;
  supplierBanksInfos?: ISupplierBanksInfo[] | null;
  supplierContacts?: ISupplierContacts[] | null;
  vehicleControlExpenses?: IVehicleControlExpenses[] | null;
  housings?: IHousing[] | null;
  affiliates?: IAffiliates;
  cities?: ICities;
  serviceProvided?: IServiceProvided;
}

export class Suppliers implements ISuppliers {
  constructor(
    public id?: number,
    public supplierName?: string,
    public supplierNumber?: string | null,
    public supplierPostalCode?: string | null,
    public supplierAddress?: string | null,
    public supplierAddressComplement?: string | null,
    public supplierAddressNumber?: number | null,
    public supplierAddressNeighborhood?: string | null,
    public supplierTelephone?: string | null,
    public supplierEmail?: string | null,
    public supplierContactName?: string | null,
    public supplierBanksInfos?: ISupplierBanksInfo[] | null,
    public supplierContacts?: ISupplierContacts[] | null,
    public vehicleControlExpenses?: IVehicleControlExpenses[] | null,
    public housings?: IHousing[] | null,
    public affiliates?: IAffiliates,
    public cities?: ICities,
    public serviceProvided?: IServiceProvided
  ) {}
}

export function getSuppliersIdentifier(suppliers: ISuppliers): number | undefined {
  return suppliers.id;
}
