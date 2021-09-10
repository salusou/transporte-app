import { ISuppliers } from 'app/entities/suppliers/suppliers.model';

export interface IServiceProvided {
  id?: number;
  serviceName?: string;
  suppliers?: ISuppliers[] | null;
}

export class ServiceProvided implements IServiceProvided {
  constructor(public id?: number, public serviceName?: string, public suppliers?: ISuppliers[] | null) {}
}

export function getServiceProvidedIdentifier(serviceProvided: IServiceProvided): number | undefined {
  return serviceProvided.id;
}
