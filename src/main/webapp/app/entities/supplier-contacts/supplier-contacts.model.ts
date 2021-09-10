import { ISuppliers } from 'app/entities/suppliers/suppliers.model';

export interface ISupplierContacts {
  id?: number;
  supplierContactName?: string;
  supplierContactPhone?: string | null;
  supplierContactEmail?: string | null;
  suppliers?: ISuppliers;
}

export class SupplierContacts implements ISupplierContacts {
  constructor(
    public id?: number,
    public supplierContactName?: string,
    public supplierContactPhone?: string | null,
    public supplierContactEmail?: string | null,
    public suppliers?: ISuppliers
  ) {}
}

export function getSupplierContactsIdentifier(supplierContacts: ISupplierContacts): number | undefined {
  return supplierContacts.id;
}
