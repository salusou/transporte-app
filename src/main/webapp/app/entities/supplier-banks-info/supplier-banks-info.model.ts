import { ISuppliers } from 'app/entities/suppliers/suppliers.model';

export interface ISupplierBanksInfo {
  id?: number;
  supplierBankCode?: number;
  supplierBankName?: string;
  supplierBankBranchCode?: string | null;
  supplierBankAccountNumber?: string | null;
  supplierBankUserName?: string | null;
  supplierBankPixKey?: string | null;
  supplierBankUserNumber?: string | null;
  suppliers?: ISuppliers;
}

export class SupplierBanksInfo implements ISupplierBanksInfo {
  constructor(
    public id?: number,
    public supplierBankCode?: number,
    public supplierBankName?: string,
    public supplierBankBranchCode?: string | null,
    public supplierBankAccountNumber?: string | null,
    public supplierBankUserName?: string | null,
    public supplierBankPixKey?: string | null,
    public supplierBankUserNumber?: string | null,
    public suppliers?: ISuppliers
  ) {}
}

export function getSupplierBanksInfoIdentifier(supplierBanksInfo: ISupplierBanksInfo): number | undefined {
  return supplierBanksInfo.id;
}
